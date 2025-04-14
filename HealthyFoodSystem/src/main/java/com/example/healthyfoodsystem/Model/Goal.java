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
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @Pattern(regexp = "Weight Loss|Muscle Gain|Balanced", message = "Goal must be one of: Weight Loss, Muscle Gain, Balanced")
    @Column(columnDefinition = "varchar(30) not null")
    private String name;

    @Size(max = 255)
    @Column(columnDefinition = "varchar(255)")
    private String excludedFoods;

    @NotNull
    @Positive
    @Column(columnDefinition = "double not null")
    private Double targetWeight;

    @NotNull
    @Column(columnDefinition = "int not null")
    private Integer memberId;
}
