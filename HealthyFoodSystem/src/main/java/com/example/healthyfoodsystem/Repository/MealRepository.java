package com.example.healthyfoodsystem.Repository;

import com.example.healthyfoodsystem.Model.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MealRepository extends JpaRepository<Meal,Integer> {

    Meal findMealById(Integer id);

    List<Meal> findMealsByPlanId(Integer planId);

    List<Meal> findMealsByTypeIgnoreCase(String type);

    @Query("SELECT SUM(m.protein) FROM Meal m WHERE m.planId = ?1")
    Integer getTotalProtein(Integer planId);

    @Query("SELECT SUM(m.carbs) FROM Meal m WHERE m.planId = ?1")
    Integer getTotalCarbs(Integer planId);

    @Query("SELECT SUM(m.fat) FROM Meal m WHERE m.planId = ?1")
    Integer getTotalFat(Integer planId);

    @Query("SELECT COUNT(m) FROM Meal m WHERE m.planId = ?1")
    Integer countMealsByPlanId(Integer planId);

}
