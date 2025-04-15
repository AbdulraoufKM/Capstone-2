package com.example.healthyfoodsystem.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @Size(min = 3)
    @Column(columnDefinition = "varchar(30) not null")
    private String name;

    @NotEmpty
    @Pattern(regexp = "Weight Loss|Muscle Gain|Balanced", message = "Goal must be one of: Weight Loss, Muscle Gain, Balanced")
    @Column(columnDefinition = "varchar(30) not null")
    private String goalName;

    @NotNull
    @Positive
    @Column(columnDefinition = "int not null")
    private Integer price;

    @NotNull
    @Positive
    @Column(columnDefinition = "int not null")
    private Integer calories;

    @Column(columnDefinition = "varchar(255)")
    private String description;

    @NotNull
    @Min(1)
    @Column(columnDefinition = "int not null")
    private Integer mealsPerDay;


    @NotNull
    @Column(columnDefinition = "int not null")
    private Integer restaurantId;




}
