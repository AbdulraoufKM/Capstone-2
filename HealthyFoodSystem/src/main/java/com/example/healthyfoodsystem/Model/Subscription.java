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
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @Pattern(regexp = "Daily|Weekly|Monthly|Yearly", message = "Type must be one of: Daily, Weekly, Monthly, Yearly")
    @Column(columnDefinition = "varchar(20) not null")
    private String type;



    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(columnDefinition = "date not null")
    private LocalDate startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(columnDefinition = "date not null")
    private LocalDate endDate;

    @Pattern(regexp = "Active|NotActive|Canceled", message = "Status must be one of: Active, NotActive, Canceled")
    @Column(columnDefinition = "varchar(15)")
    private String status;


    @PositiveOrZero
    @Column(columnDefinition = "double ")
    private Double price;

    @NotNull
    @Column(columnDefinition = "int not null")
    private Integer memberId;

    @NotNull
    @Column(columnDefinition = "int not null")
    private Integer planId;

    @Column(columnDefinition = "int")
    private Integer ratingId;

    @Transient
    private Member member;





}
