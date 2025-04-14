package com.example.healthyfoodsystem.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @Size(min = 3)
    @Column(columnDefinition = "varchar(20) not null")
    private String name;

    @Min(15)
    @Max(100)
    @Column(columnDefinition = "int not null")
    private Integer age;


    @NotEmpty
    @Pattern(regexp = "male|female", message = "Gender must be either 'male' or 'female'")
    @Column(columnDefinition = "varchar(20) not null")
    private String gender;


    @NotEmpty
    @Column(columnDefinition = "varchar(20) not null unique")
    private String phone;

    @NotEmpty
    @Email
    @Column(columnDefinition = "varchar(50) not null unique")
    private String email;

    @NotEmpty
    @Size(min = 5)
    @Column(columnDefinition = "varchar(50) not null")
    private String city;


    @Positive
    @NotNull
    @Column(columnDefinition = "double not null")
    private Double height;

    @Positive
    @NotNull
    @Column(columnDefinition = "double not null")
    private Double weight;




}
