package ru.javaops.bootjava.web.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.javaops.bootjava.model.Menu;
import ru.javaops.bootjava.service.MenuService;
import ru.javaops.bootjava.util.MenuMapper;
import ru.javaops.bootjava.web.dto.MenuTo;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/menus")
@RequiredArgsConstructor
public class AdminMenuController {

    private final MenuService menuService;

    @GetMapping("/restaurant/{restaurantId}")
    public List<MenuTo> getByRestaurantAndDate(@PathVariable int restaurantId,
                                               @RequestParam LocalDate date) {
        return menuService.getMenuByRestaurantAndDate(restaurantId, date).stream()
                .map(MenuMapper::toTo)
                .collect(Collectors.toList());
    }

    @PostMapping("/restaurant/{restaurantId}")
    @ResponseStatus(HttpStatus.CREATED)
    public MenuTo create(@PathVariable int restaurantId,
                         @Valid @RequestBody MenuTo menuTo) {
        Menu menu = MenuMapper.fromTo(menuTo);
        Menu created = menuService.create(restaurantId, menu);
        return MenuMapper.toTo(created);
    }

    @PutMapping("/{menuId}/restaurant/{restaurantId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable int restaurantId,
                       @PathVariable int menuId,
                       @Valid @RequestBody MenuTo menuTo) {
        Menu menu = MenuMapper.fromTo(menuTo);
        menuService.update(restaurantId, menuId, menu);
    }

    @DeleteMapping("/{menuId}/restaurant/{restaurantId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int restaurantId,
                       @PathVariable int menuId) {
        menuService.delete(restaurantId, menuId);
    }
}
