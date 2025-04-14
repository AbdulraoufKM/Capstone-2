package com.example.healthyfoodsystem.Controller;

import com.example.healthyfoodsystem.Api.ApiResponse;
import com.example.healthyfoodsystem.Model.Meal;
import com.example.healthyfoodsystem.Service.MealService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/meal")
@RequiredArgsConstructor
public class MealController {

    private final MealService mealService;

    @GetMapping("/get")
    public ResponseEntity getAllMeals() {
        return ResponseEntity.status(200).body(mealService.getAllMeals());
    }

    @PostMapping("/restaurant/add/{restaurantId}")
    public ResponseEntity addMealByRestaurant(@PathVariable Integer restaurantId, @Valid @RequestBody Meal meal, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }

        Boolean isAdded = mealService.addMealByRestaurant(restaurantId, meal);
        return isAdded ? ResponseEntity.status(200).body(new ApiResponse("Meal added successfully")):ResponseEntity.status(400).body(new ApiResponse("Invalid plan ID"));
    }

    @PutMapping("/restaurant-update-meal/{restaurantId}/{mealId}")
    public ResponseEntity updateMealByRestaurant(@PathVariable Integer restaurantId, @PathVariable Integer mealId, @RequestBody @Valid Meal meal) {
        Boolean updated = mealService.updateMealByRestaurant(restaurantId, mealId, meal);
        return updated ? ResponseEntity.status(200).body(new ApiResponse("Meal updated by restaurant")) : ResponseEntity.status(400).body(new ApiResponse("Not allowed or meal not found"));
    }


    @DeleteMapping("/restaurant-delete-meal/{restaurantId}/{mealId}")
    public ResponseEntity deleteMealByRestaurant(@PathVariable Integer restaurantId, @PathVariable Integer mealId) {
        Boolean deleted = mealService.deleteMealByRestaurant(restaurantId, mealId);
        return deleted ? ResponseEntity.status(200).body(new ApiResponse("Meal deleted by restaurant")) : ResponseEntity.status(400).body(new ApiResponse("Not allowed or meal not found"));
    }


    @GetMapping("/plan/{planId}")
    public ResponseEntity getMealsByPlan(@PathVariable Integer planId) {
        List<Meal> meals = mealService.getMealsByPlanId(planId);
        return ResponseEntity.status(200).body(meals);
    }

    @GetMapping("/type/{type}")
    public ResponseEntity getMealsByType(@PathVariable String type) {
        List<Meal> meals = mealService.getMealsByType(type);
        return ResponseEntity.status(200).body(meals);
    }





}
