package com.example.healthyfoodsystem.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Min(1)
    @Max(5)
    @Column(columnDefinition = "int not null")
    private Integer stars;

    @Column(columnDefinition = "varchar(255)")
    private String comment;

    @NotNull
    @Column(columnDefinition = "int not null")
    private Integer memberId;

    @NotNull
    @Column(columnDefinition = "int not null")
    private Integer subscriptionId;

    private LocalDate createdAt;
}
