package ru.javaops.bootjava.util;

import lombok.experimental.UtilityClass;
import ru.javaops.bootjava.model.Restaurant;
import ru.javaops.bootjava.to.RestaurantTo;

@UtilityClass
public class RestaurantMapper {

    public static RestaurantTo toTo(Restaurant restaurant) {
        return new RestaurantTo(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getAddress()
        );
    }

    public static Restaurant fromTo(RestaurantTo restaurantTo) {
        return new Restaurant(
                restaurantTo.getId(),
                restaurantTo.getName(),
                restaurantTo.getAddress()
        );
    }
}