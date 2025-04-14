package com.example.healthyfoodsystem.Service;

import com.example.healthyfoodsystem.Model.Plan;
import com.example.healthyfoodsystem.Model.Rating;
import com.example.healthyfoodsystem.Model.Restaurant;
import com.example.healthyfoodsystem.Model.Subscription;
import com.example.healthyfoodsystem.Repository.PlanRepository;
import com.example.healthyfoodsystem.Repository.RatingRepository;
import com.example.healthyfoodsystem.Repository.RestaurantRepository;
import com.example.healthyfoodsystem.Repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final RatingRepository ratingRepository;
    private final PlanRepository planRepository;

    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    public void addRestaurant(Restaurant restaurant) {
        restaurantRepository.save(restaurant);
    }

    public Boolean updateRestaurant(Integer id, Restaurant restaurant) {
        Restaurant oldRestaurant = restaurantRepository.findRestaurantById(id);
        if (oldRestaurant == null) {
            return false;
        }

        oldRestaurant.setName(restaurant.getName());
        oldRestaurant.setPhone(restaurant.getPhone());
        oldRestaurant.setCity(restaurant.getCity());

        restaurantRepository.save(oldRestaurant);
        return true;
    }

    public Boolean deleteRestaurant(Integer id) {
        Restaurant restaurant = restaurantRepository.findRestaurantById(id);
        if (restaurant == null) {
            return false;
        }

        restaurantRepository.delete(restaurant);
        return true;
    }


    // 13 Get top 3 restaurants by average rating
    public List<String> AverageRating() {
        List<Restaurant> allRestaurants = restaurantRepository.findAll();
        List<Subscription> allSubscriptions = subscriptionRepository.findAll();
        List<Rating> allRatings = ratingRepository.findAll();
        List<Plan> allPlans = planRepository.findAll();

        List<String> result = new ArrayList<>();

        for (Restaurant restaurant : allRestaurants) {
            int total = 0;
            int count = 0;
            for (Plan plan : allPlans) {
                if (plan.getRestaurantId().equals(restaurant.getId())) {
                    for (Subscription sub : allSubscriptions) {
                        if (sub.getPlanId().equals(plan.getId())) {
                            for (Rating rating : allRatings) {
                                if (rating.getSubscriptionId() != null && rating.getSubscriptionId().equals(sub.getId())) {
                                    total += rating.getStars();
                                    count++;
                                }
                            }
                        }
                    }
                }
            }

            if (count > 0) {
                double avg = (double) total / count;
                result.add(restaurant.getName() + " - Rating: " + avg);
            }

            //Top 3 restaurant
            if (result.size() == 3){
                return result;
            }
        }

        return result;
    }


    // 14 Get nearby restaurants based on member city
    public List<Restaurant> getNearbyRestaurants(Integer memberId) {
        return restaurantRepository.findNearbyRestaurants(memberId);
    }



}
