package com.example.healthyfoodsystem.Controller;

import com.example.healthyfoodsystem.Api.ApiResponse;
import com.example.healthyfoodsystem.Model.Restaurant;
import com.example.healthyfoodsystem.Service.RestaurantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/restaurant")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping("/get")
    public ResponseEntity getAllRestaurants() {
        return ResponseEntity.status(200).body(restaurantService.getAllRestaurants());
    }

    @PostMapping("/add")
    public ResponseEntity addRestaurant(@Valid @RequestBody Restaurant restaurant, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        restaurantService.addRestaurant(restaurant);
        return ResponseEntity.status(200).body(new ApiResponse("Restaurant added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateRestaurant(@PathVariable Integer id, @Valid @RequestBody Restaurant restaurant, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }

        Boolean isUpdated = restaurantService.updateRestaurant(id, restaurant);
        if (isUpdated) {
            return ResponseEntity.status(200).body(new ApiResponse("Restaurant updated successfully"));
        }

        return ResponseEntity.status(400).body(new ApiResponse("Update failed"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteRestaurant(@PathVariable Integer id) {
        Boolean isDeleted = restaurantService.deleteRestaurant(id);
        if (isDeleted) {
            return ResponseEntity.status(200).body(new ApiResponse("Restaurant deleted successfully"));
        }

        return ResponseEntity.status(400).body(new ApiResponse("Delete failed"));
    }

    @GetMapping("/avg-ratings")
    public ResponseEntity getRatedRestaurants() {
        return ResponseEntity.status(200).body(restaurantService.AverageRating());
    }

    @GetMapping("/nearby/{memberId}")
    public ResponseEntity getNearbyRestaurants(@PathVariable Integer memberId) {
        List<Restaurant> restaurants = restaurantService.getNearbyRestaurants(memberId);
        if (restaurants.isEmpty()) {
            return ResponseEntity.status(404).body(new ApiResponse("No nearby restaurants found"));
        }
        return ResponseEntity.status(200).body(restaurants);
    }

}

