package com.example.healthyfoodsystem.Controller;

import com.example.healthyfoodsystem.Api.ApiResponse;
import com.example.healthyfoodsystem.Model.Subscription;
import com.example.healthyfoodsystem.Service.SubscriptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/subscription")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @GetMapping("/get")
    public ResponseEntity getAllSubscriptions() {
        return ResponseEntity.status(200).body(subscriptionService.getAllSubscriptions());
    }

    @PostMapping("/add")
    public ResponseEntity registerSubscription(@Valid @RequestBody Subscription subscription, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }

        Boolean isAdded = subscriptionService.registerSubscription(subscription);
        if (!isAdded) {
            return ResponseEntity.status(400).body(new ApiResponse("Failed: Already subscribed or invalid type"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("Subscription added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateSubscription(@PathVariable Integer id, @Valid @RequestBody Subscription subscription, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }

        Boolean isUpdated = subscriptionService.updateSubscription(id, subscription);
        if (!isUpdated) {
            return ResponseEntity.status(400).body(new ApiResponse("Update failed"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("Subscription updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteSubscription(@PathVariable Integer id) {
        Boolean isDeleted = subscriptionService.deleteSubscription(id);
        if (!isDeleted) {
            return ResponseEntity.status(400).body(new ApiResponse("Delete failed"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("Subscription deleted successfully"));
    }


    @GetMapping("/subs-restaurant/{restaurantId}")
    public ResponseEntity getSubsByRestaurant(@PathVariable Integer restaurantId) {
        List<Subscription> subs = subscriptionService.SubsMemberByRestaurant(restaurantId);
        if (subs.isEmpty()) {
            return ResponseEntity.status(404).body(new ApiResponse("No subscriptions found for this restaurant"));
        }
        return ResponseEntity.status(200).body(subs);
    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity cancelSubscription(@PathVariable Integer id) {
        boolean canceled = subscriptionService.cancelSubscription(id);
        if (!canceled) {
            return ResponseEntity.status(400).body(new ApiResponse("Cancel failed or subscription not active"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("Subscription canceled successfully"));
    }

    @PutMapping("/extend/{id}")
    public ResponseEntity extendSubscription(@PathVariable Integer id) {
        boolean extended = subscriptionService.extendSubscription(id);
        if (!extended) {
            return ResponseEntity.status(400).body(new ApiResponse("Extend failed"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("Subscription extended"));
    }

    @PutMapping("/resubscribe/{oldSubId}")
    public ResponseEntity resubscribe(@PathVariable Integer oldSubId) {
        boolean done = subscriptionService.resubscribeToPlan(oldSubId);
        if (!done) {
            return ResponseEntity.status(400).body(new ApiResponse("Resubscription failed"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("Resubscribed successfully"));
    }








}
