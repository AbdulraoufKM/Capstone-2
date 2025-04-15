package com.example.healthyfoodsystem.Service;

import com.example.healthyfoodsystem.Model.Goal;
import com.example.healthyfoodsystem.Model.Plan;
import com.example.healthyfoodsystem.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanService {

    private final PlanRepository planRepository;
    private final GoalRepository goalRepository;
    private final RestaurantRepository restaurantRepository;
    private final GoalService goalService;
    private final MealRepository mealRepository;
    private final SubscriptionRepository subscriptionRepository;

    public List<Plan> getAllPlans() {
        return planRepository.findAll();
    }


    public Boolean addPlan(Plan plan) {
        if (restaurantRepository.findRestaurantById(plan.getRestaurantId()) == null) {
            return false;
        }

        planRepository.save(plan);
        return true;
    }

    public Boolean updatePlan(Integer id, Plan plan) {
        Plan oldPlan = planRepository.findPlanById(id);
        if (oldPlan == null) {
            return false;
        }

        oldPlan.setName(plan.getName());
        oldPlan.setGoalName(plan.getGoalName());
        oldPlan.setPrice(plan.getPrice());
        oldPlan.setRestaurantId(plan.getRestaurantId());
        oldPlan.setDescription(plan.getDescription());
        oldPlan.setCalories(plan.getCalories());
        oldPlan.setMealsPerDay(plan.getMealsPerDay());

        planRepository.save(oldPlan);
        return true;
    }

    public Boolean deletePlan(Integer id) {
        Plan plan = planRepository.findPlanById(id);
        if (plan == null) {
            return false;
        }

        planRepository.delete(plan);
        return true;
    }

    // 7 Filter plans by price range
    public List<Plan> ByPriceRange(Integer min, Integer max) {
        return planRepository.findByPriceRange(min, max);
    }

    // 8 Get total macros in a plan
    public String getMacro(Integer planId) {
        Integer protein = mealRepository.getTotalProtein(planId);
        Integer carbs = mealRepository.getTotalCarbs(planId);
        Integer fat = mealRepository.getTotalFat(planId);

        return "Protein: " + (protein != null ? protein : 0) +
                ", Carbs: " + (carbs != null ? carbs : 0) +
                ", Fat: " + (fat != null ? fat : 0);
    }

    // 9 Count meals in a plan
    public Integer countMealsInPlan(Integer planId) {
        return mealRepository.countMealsByPlanId(planId);
    }

    // 10 Count subscriptions for a plan
    public Integer countSubscriptionsByPlanId(Integer planId) {
        return subscriptionRepository.countByPlanId(planId);
    }

    //11 Get plans by restaurant name
    public List<Plan> RestaurantByName(String restaurantName){
        return planRepository.findPlanByRestaurantName(restaurantName);
    }

    //12 Suggest plans based on calories and goal name
    public List<Plan> suggestPlansByCalories(Integer memberId) {
        Goal goal = goalRepository.findByMemberId(memberId);
        if (goal == null) {
            return new ArrayList<>();
        }

        Double recommendedCalories = goalService.calculateRecommendedCalories(goal);
        if (recommendedCalories == null) {
            return new ArrayList<>();
        }

        List<Plan> allPlans = planRepository.findAll();
        List<Plan> matchingPlans = new ArrayList<>();

        for (Plan plan : allPlans) {
            if (plan.getCalories() <= recommendedCalories && plan.getGoalName().trim().equalsIgnoreCase(goal.getName().trim())) {
                matchingPlans.add(plan);
            }
        }

        return matchingPlans;
    }


}



