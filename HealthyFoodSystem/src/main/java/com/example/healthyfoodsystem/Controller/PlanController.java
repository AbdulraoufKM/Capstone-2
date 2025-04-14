package com.example.healthyfoodsystem.Controller;

import com.example.healthyfoodsystem.Api.ApiResponse;
import com.example.healthyfoodsystem.Model.Plan;
import com.example.healthyfoodsystem.Service.PlanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/plan")
@RequiredArgsConstructor
public class PlanController {

    private final PlanService planService;

    @GetMapping("/get")
    public ResponseEntity getAllPlans() {
        return ResponseEntity.status(200).body(planService.getAllPlans());
    }

    @PostMapping("/add")
    public ResponseEntity addPlan(@Valid @RequestBody Plan plan, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        boolean added = planService.addPlan(plan);
        return added ? ResponseEntity.status(200).body(new ApiResponse("Plan added successfully")) : ResponseEntity.status(400).body(new ApiResponse("Invalid restaurant ID"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updatePlan(@PathVariable Integer id, @Valid @RequestBody Plan plan, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        boolean updated = planService.updatePlan(id, plan);
        return updated ? ResponseEntity.status(200).body(new ApiResponse("Plan updated successfully")) : ResponseEntity.status(400).body(new ApiResponse("Update failed"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deletePlan(@PathVariable Integer id) {
        boolean deleted = planService.deletePlan(id);
        return deleted ? ResponseEntity.status(200).body(new ApiResponse("Plan deleted successfully")) : ResponseEntity.status(400).body(new ApiResponse("Delete failed"));
    }


    @GetMapping("/suggest/{memberId}")
    public ResponseEntity suggestPlans(@PathVariable Integer memberId) {
        List<Plan> plans = planService.suggestPlansByCalories(memberId);
        if (plans.isEmpty()) {
            return ResponseEntity.status(404).body(new ApiResponse("No matching plans found"));
        }
        return ResponseEntity.status(200).body(plans);
    }

    @GetMapping("/filterByPrice/{min}/{max}")
    public ResponseEntity filterByPrice(@PathVariable Integer min, @PathVariable Integer max) {
        return ResponseEntity.status(200).body(planService.ByPriceRange(min, max));
    }

    @GetMapping("/macros/{planId}")
    public ResponseEntity getMacro(@PathVariable Integer planId) {
        return ResponseEntity.status(200).body(planService.getMacro(planId));
    }

    @GetMapping("/meals/count/{planId}")
    public ResponseEntity countMeals(@PathVariable Integer planId) {
        return ResponseEntity.status(200).body("Total meals: " + planService.countMealsInPlan(planId));
    }

    @GetMapping("/subscriptions-count/{planId}")
    public ResponseEntity countSubscriptions(@PathVariable Integer planId) {
        return ResponseEntity.status(200).body("Total subscriptions for this plan: " + planService.countSubscriptionsByPlanId(planId));
    }

    @GetMapping("/restaurant-plans-ByName/{restaurantName}")
    public ResponseEntity restaurantPlansByName(@PathVariable String restaurantName) {
        return ResponseEntity.status(200).body(planService.RestaurantByName(restaurantName));
    }




}