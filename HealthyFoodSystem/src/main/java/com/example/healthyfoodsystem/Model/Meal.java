package com.example.healthyfoodsystem.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Meal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @Size(min = 3)
    @Column(columnDefinition = "varchar(30) not null")
    private String name;

    @NotEmpty
    @Pattern(regexp = "Breakfast|Lunch|Dinner|Snack", message = "Type must be one of: Breakfast, Lunch, Dinner, Snack")
    @Column(columnDefinition = "varchar(30) not null")
    private String type;

    @NotEmpty
    @Size(min = 3)
    @Column(columnDefinition = "varchar(255) not null")
    private String description;

    @PositiveOrZero
    @Column(columnDefinition = "int not null")
    private Integer protein;

    @PositiveOrZero
    @Column(columnDefinition = "int not null")
    private Integer carbs;

    @PositiveOrZero
    @Column(columnDefinition = "int not null")
    private Integer fat;


    @PositiveOrZero
    @Column(columnDefinition = "int not null")
    private Integer calories;

    @NotNull
    @Column(columnDefinition = "int not null")
    private Integer planId;
}
