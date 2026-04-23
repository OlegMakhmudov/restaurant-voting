package ru.javaops.bootjava.to;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoteTo {
    private Integer id;
    private Integer restaurantId;
    private String restaurantName;
    private LocalDate date;
    private LocalTime time;
}