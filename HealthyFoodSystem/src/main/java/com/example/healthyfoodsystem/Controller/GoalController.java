package com.example.healthyfoodsystem.Controller;

import com.example.healthyfoodsystem.Api.ApiResponse;
import com.example.healthyfoodsystem.Model.Goal;
import com.example.healthyfoodsystem.Service.GoalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/goal")
@RequiredArgsConstructor
public class GoalController {

    private final GoalService goalService;

    @PostMapping("/add")
    public ResponseEntity addGoal(@Valid @RequestBody Goal goal, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }

        boolean added = goalService.addGoal(goal);

        if (!added) {
            return ResponseEntity.status(400).body(new ApiResponse("Goal not added: either member not found or goal already exists"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("Goal added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateGoal(@PathVariable Integer id, @Valid @RequestBody Goal goal, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }

        boolean updated = goalService.updateGoal(id, goal);
        if (!updated) {
            return ResponseEntity.status(404).body(new ApiResponse("Goal not found"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("Goal updated successfully"));
    }


    @GetMapping("/calories/{memberId}")
    public ResponseEntity getRecommendedCalories(@PathVariable Integer memberId) {
        Goal goal = goalService.getGoalByMemberId(memberId);

        if (goal == null) {
            return ResponseEntity.status(404).body(new ApiResponse("Goal not found for this member"));
        }

        Double calories = goalService.calculateRecommendedCalories(goal);
        if (calories == null) {
            return ResponseEntity.status(400).body(new ApiResponse("Cannot calculate calories: invalid data"));
        }

        return ResponseEntity.status(200).body("Recommended daily calories: " + calories);
    }
}
