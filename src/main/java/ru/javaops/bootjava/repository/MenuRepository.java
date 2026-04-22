package ru.javaops.bootjava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.bootjava.model.Menu;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface MenuRepository extends JpaRepository<Menu, Integer> {

    @Query("SELECT m FROM Menu m JOIN FETCH m.restaurant WHERE m.date = :date ORDER BY m.restaurant.name, m.dishName")
    List<Menu> findAllByDateWithRestaurant(@Param("date") LocalDate date);

    @Query("SELECT m FROM Menu m WHERE m.restaurant.id = :restaurantId AND m.date = :date")
    List<Menu> findAllByRestaurantIdAndDate(@Param("restaurantId") int restaurantId, @Param("date") LocalDate date);

    @Transactional
    @Modifying
    @Query("DELETE FROM Menu m WHERE m.restaurant.id = :restaurantId AND m.date = :date")
    int deleteByRestaurantIdAndDate(@Param("restaurantId") int restaurantId, @Param("date") LocalDate date);

    Optional<Menu> findByIdAndRestaurantId(int id, int restaurantId);

}
