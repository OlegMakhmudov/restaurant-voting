package ru.javaops.bootjava.util;

import lombok.experimental.UtilityClass;
import ru.javaops.bootjava.model.Menu;
import ru.javaops.bootjava.web.dto.MenuTo;

@UtilityClass
public class MenuMapper {

    public static MenuTo toTo(Menu menu) {
        return new MenuTo(
                menu.getId(),
                menu.getDishName(),
                menu.getPrice(),
                menu.getDate(),
                menu.getRestaurant().getId()
        );
    }

    public static Menu fromTo(MenuTo menuTo) {
        Menu menu = new Menu();
        menu.setId(menuTo.getId());
        menu.setDishName(menuTo.getDishName());
        menu.setPrice(menuTo.getPrice());
        menu.setDate(menuTo.getDate());
        return menu;
    }
}
