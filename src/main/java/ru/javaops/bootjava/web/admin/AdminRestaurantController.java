package ru.javaops.bootjava.web.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.javaops.bootjava.model.Restaurant;
import ru.javaops.bootjava.service.RestaurantService;
import ru.javaops.bootjava.util.RestaurantMapper;
import ru.javaops.bootjava.web.dto.RestaurantTo;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/admin/restaurants")
@RequiredArgsConstructor
public class AdminRestaurantController {
    private final RestaurantService restaurantService;

    @GetMapping
    public List<RestaurantTo> getAll() {
        return restaurantService.getAll().stream()
                .map(RestaurantMapper::toTo)
                .toList();
    }

    @GetMapping("/{id}")
    public RestaurantTo get(int id) {
        return RestaurantMapper.toTo(restaurantService.get(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestaurantTo create (@Valid @RequestBody RestaurantTo restaurantTo) {
        Restaurant restaurant = RestaurantMapper.fromTo(restaurantTo);
        Restaurant created = restaurantService.create(restaurant);
        return RestaurantMapper.toTo(created);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable int id, @RequestBody RestaurantTo restaurantTo) {
        Restaurant restaurant = RestaurantMapper.fromTo(restaurantTo);
        restaurantService.update(id, restaurant);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        restaurantService.delete(id);
    }
}
