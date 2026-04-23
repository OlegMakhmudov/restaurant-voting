package ru.javaops.bootjava.to;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuTo {
    private Integer id;
    private String dishName;
    private BigDecimal price;
    private LocalDate date;
    private Integer restaurantId;
}