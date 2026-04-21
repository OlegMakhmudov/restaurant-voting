package ru.javaops.bootjava.model;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "menus", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"restaurant_id", "date", "dish_name"}, name = "uk_menu_restaurant_date_dish")
})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString(callSuper = true)
public class Menu extends AbstractBaseEntity{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    private Restaurant restaurant;

    @Column(name = "date", nullable = false)
    @NotNull
    private LocalDate date;

    @Column(name = "dish_name", nullable = false)
    @NotBlank
    @Size(min = 2, max = 100)
    private String dishName;

    @Column(name = "price", nullable = false)
    @NotNull
    @Positive
    private BigDecimal price;

    public Menu(Integer id, Restaurant restaurant, LocalDate date, String dishName, BigDecimal price) {
        super(id);
        this.restaurant = restaurant;
        this.date = date;
        this.dishName = dishName;
        this.price = price;
    }
}
