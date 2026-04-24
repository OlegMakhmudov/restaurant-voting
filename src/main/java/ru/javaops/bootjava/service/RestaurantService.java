package ru.javaops.bootjava.service;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.bootjava.model.Restaurant;
import ru.javaops.bootjava.repository.RestaurantRepository;
import ru.javaops.bootjava.util.RestaurantMapper;
import ru.javaops.bootjava.web.dto.RestaurantTo;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Cacheable(value = "restaurants", unless = "#result == null || #result.isEmpty()")
    public List<Restaurant> getAll() {
        return restaurantRepository.findAllOrderbyName();
    }

    public Restaurant get(int id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurant with id " + id + " not found"));
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    @Transactional
    public Restaurant create(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    @Transactional
    public void update(int id, Restaurant updatedRestaurant) {
        Restaurant existing = get(id);
        existing.setName(updatedRestaurant.getName());
        existing.setAddress(updatedRestaurant.getAddress());
        restaurantRepository.save(existing);
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    @Transactional
    public void delete(int id) {
        restaurantRepository.deleteById(id);
    }

    public Page<RestaurantTo> getAll(Pageable pageable) {
        return restaurantRepository.findAll(pageable)
                .map(RestaurantMapper::toTo);
    }
}