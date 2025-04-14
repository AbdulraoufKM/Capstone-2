package com.example.healthyfoodsystem.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor

public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @Size(min = 3)
    @Column(columnDefinition = "varchar(20) not null ")
    private String name;

    @NotEmpty
    @Column(columnDefinition = "varchar(20) not null unique")
    private String phone;

    @NotEmpty
    @Column(columnDefinition = "varchar(50) not null")
    private String city;



}
