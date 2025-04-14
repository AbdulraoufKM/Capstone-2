package com.example.healthyfoodsystem.Repository;

import com.example.healthyfoodsystem.Model.Plan;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanRepository extends JpaRepository<Plan,Integer> {

    Plan findPlanById(Integer id);

    @Query("SELECT p FROM Plan p WHERE p.price BETWEEN ?1 AND ?2")
    List<Plan> findByPriceRange(Integer min, Integer max);

    @Query("SELECT p FROM Plan p JOIN Restaurant r ON p.restaurantId = r.id WHERE r.name = ?1")
    List<Plan> findPlanByRestaurantName(String restaurantName );

    @Query("SELECT p FROM Plan p JOIN Restaurant r ON p.restaurantId = r.id WHERE r.city = ?1")
    List<Plan> findPlanByRestaurantCity(String restaurantName );

    List<Plan> findPlanByRestaurantId( Integer restaurantId);




}
