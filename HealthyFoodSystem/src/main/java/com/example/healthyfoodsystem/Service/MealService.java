package com.example.healthyfoodsystem.Service;

import com.example.healthyfoodsystem.Model.Meal;
import com.example.healthyfoodsystem.Model.Plan;
import com.example.healthyfoodsystem.Repository.MealRepository;
import com.example.healthyfoodsystem.Repository.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MealService {

    private final MealRepository mealRepository;
    private final PlanRepository planRepository;

    public List<Meal> getAllMeals() {
        return mealRepository.findAll();
    }

    // 16 add Meal By Restaurant and calculate Calories of the mael
    public Boolean addMealByRestaurant(Integer restaurantId, Meal meal) {
        Plan plan = planRepository.findPlanById(meal.getPlanId());
        if (plan == null || !plan.getRestaurantId().equals(restaurantId)) {
            return false;
        }

        int calculatedCalories = (meal.getProtein() * 4) + (meal.getCarbs() * 4) + (meal.getFat() * 9);
        meal.setCalories(calculatedCalories);

        mealRepository.save(meal);
        return true;
    }

    //17 update Meal By Restaurant
    public Boolean updateMealByRestaurant(Integer restaurantId, Integer mealId, Meal newMeal) {
        Meal meal = mealRepository.findMealById(mealId);
        if (meal == null) return false;

        Plan plan = planRepository.findPlanById(meal.getPlanId());
        if (plan == null || !plan.getRestaurantId().equals(restaurantId)) return false;

        meal.setName(newMeal.getName());
        meal.setDescription(newMeal.getDescription());
        meal.setType(newMeal.getType());
        meal.setProtein(newMeal.getProtein());
        meal.setCarbs(newMeal.getCarbs());
        meal.setFat(newMeal.getFat());
        int calculatedCalories = (newMeal.getProtein() * 4) + (newMeal.getCarbs() * 4) + (newMeal.getFat() * 9);
        meal.setCalories(calculatedCalories);

        mealRepository.save(meal);
        return true;
    }

    // 18 delete Meal By Restaurant
    public Boolean deleteMealByRestaurant(Integer restaurantId, Integer mealId) {
        Meal meal = mealRepository.findMealById(mealId);
        if (meal == null) return false;

        Plan plan = planRepository.findPlanById(meal.getPlanId());
        if (plan == null || !plan.getRestaurantId().equals(restaurantId)) return false;

        mealRepository.delete(meal);
        return true;
    }


    // 19 (extra) Get meals by planId
    public List<Meal> getMealsByPlanId(Integer planId) {
        return mealRepository.findMealsByPlanId(planId);
    }

    // 20 (extra) Get meals by type [Breakfast, lunch ...]
    public List<Meal> getMealsByType(String type) {
        return mealRepository.findMealsByTypeIgnoreCase(type);
    }


}
