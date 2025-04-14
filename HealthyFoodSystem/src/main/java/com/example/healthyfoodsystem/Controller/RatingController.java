package com.example.healthyfoodsystem.Controller;

import com.example.healthyfoodsystem.Api.ApiResponse;
import com.example.healthyfoodsystem.Model.Rating;
import com.example.healthyfoodsystem.Service.RatingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rating")
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    @GetMapping("/get")
    public ResponseEntity getAllRatings() {
        return ResponseEntity.status(200).body(ratingService.getAllRatings());
    }

    @PostMapping("/add")
    public ResponseEntity addRating(@Valid @RequestBody Rating rating, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }

        Boolean isAdded = ratingService.addRating(rating);
        if (!isAdded) {
            return ResponseEntity.status(403).body(new ApiResponse("You can only rate your own subscription"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("Rating added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateRating(@PathVariable Integer id, @Valid @RequestBody Rating rating, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }

        Boolean isUpdated = ratingService.updateRating(id, rating);
        if (!isUpdated) {
            return ResponseEntity.status(400).body(new ApiResponse("Update failed"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("Rating updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteRating(@PathVariable Integer id) {
        Boolean isDeleted = ratingService.deleteRating(id);
        if (!isDeleted) {
            return ResponseEntity.status(400).body(new ApiResponse("Delete failed"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("Rating deleted successfully"));
    }
}
