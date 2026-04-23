package ru.javaops.bootjava.service;

import lombok.AllArgsConstructor;
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

    public List<Menu> getTodayMenu() {
        return menuRepository.findAllByDateWithRestaurant(LocalDate.now());
    }

    public List<Menu> getMenuByRestaurantAndDate(int restaurantId, LocalDate date) {
        return menuRepository.findAllByRestaurantIdAndDate(restaurantId, date);
    }

    @Transactional
    public Menu create(int restaurantId, Menu menu) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        // ПРОВЕРКА: сколько уже блюд у ресторана на сегодня
        LocalDate today = LocalDate.now();
        long todayMenuCount = menuRepository.findAllByRestaurantIdAndDate(restaurantId, today).size();
        if (todayMenuCount >= 5) {
            throw new RuntimeException("Menu cannot have more than 5 dishes per day");
        }

        menu.setRestaurant(restaurant);
        menu.setDate(today);
        return menuRepository.save(menu);
    }

    @Transactional
    public void update(int restaurantId, int menuId, Menu updatedMenu) {
        Menu existingMenu = menuRepository.findByIdAndRestaurantId(menuId, restaurantId).orElseThrow(() -> new RuntimeException("Menu with id " + menuId + " not found"));
        existingMenu.setDishName(updatedMenu.getDishName());
        existingMenu.setPrice(updatedMenu.getPrice());
        menuRepository.save(existingMenu);
    }

    @Transactional
    public void delete(int restaurantId, int menuId) {
        Menu menu = menuRepository.findByIdAndRestaurantId(menuId, restaurantId).orElseThrow(() -> new RuntimeException("Menu with id " + menuId + " not found"));
        menuRepository.delete(menu);
    }

    @Transactional
    public void deleteAllByRestaurantAndDate(int restaurantId, LocalDate date) {
        menuRepository.deleteByRestaurantIdAndDate(restaurantId, date);
    }

}
