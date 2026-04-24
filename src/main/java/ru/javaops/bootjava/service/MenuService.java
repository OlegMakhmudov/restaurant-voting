package ru.javaops.bootjava.service;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.bootjava.model.Menu;
import ru.javaops.bootjava.model.Restaurant;
import ru.javaops.bootjava.repository.MenuRepository;
import ru.javaops.bootjava.repository.RestaurantRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class MenuService {

    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;

    @Cacheable(value = "todayMenu", unless = "#result == null || #result.isEmpty()")
    public List<Menu> getTodayMenu() {
        return menuRepository.findAllByDateWithRestaurant(LocalDate.now());
    }

    // Кэшировать по restaurantId и date
    @Cacheable(value = "menuByRestaurantAndDate", key = "#restaurantId + '_' + #date", unless = "#result == null")
    public List<Menu> getMenuByRestaurantAndDate(int restaurantId, LocalDate date) {
        return menuRepository.findAllByRestaurantIdAndDate(restaurantId, date);
    }

    @CacheEvict(value = {"todayMenu", "menuByRestaurantAndDate"}, allEntries = true)
    @Transactional
    public Menu create(int restaurantId, Menu menu) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        LocalDate today = LocalDate.now();
        long todayMenuCount = menuRepository.findAllByRestaurantIdAndDate(restaurantId, today).size();
        if (todayMenuCount >= 5) {
            throw new RuntimeException("Menu cannot have more than 5 dishes per day");
        }

        menu.setRestaurant(restaurant);
        menu.setDate(today);
        return menuRepository.save(menu);
    }

    @CacheEvict(value = {"todayMenu", "menuByRestaurantAndDate"}, allEntries = true)
    @Transactional
    public void update(int restaurantId, int menuId, Menu updatedMenu) {
        Menu existingMenu = menuRepository.findByIdAndRestaurantId(menuId, restaurantId)
                .orElseThrow(() -> new RuntimeException("Menu with id " + menuId + " not found"));
        existingMenu.setDishName(updatedMenu.getDishName());
        existingMenu.setPrice(updatedMenu.getPrice());
        menuRepository.save(existingMenu);
    }

    @CacheEvict(value = {"todayMenu", "menuByRestaurantAndDate"}, allEntries = true)
    @Transactional
    public void delete(int restaurantId, int menuId) {
        Menu menu = menuRepository.findByIdAndRestaurantId(menuId, restaurantId)
                .orElseThrow(() -> new RuntimeException("Menu with id " + menuId + " not found"));
        menuRepository.delete(menu);
    }

    @CacheEvict(value = {"todayMenu", "menuByRestaurantAndDate"}, allEntries = true)
    @Transactional
    public void deleteAllByRestaurantAndDate(int restaurantId, LocalDate date) {
        menuRepository.deleteByRestaurantIdAndDate(restaurantId, date);
    }
}
