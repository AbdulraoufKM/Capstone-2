package com.example.healthyfoodsystem.Repository;

import com.example.healthyfoodsystem.Model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant,Integer> {

    Restaurant findRestaurantById(Integer id);

    @Query("SELECT r FROM Restaurant r, Member m WHERE m.id = ?1 AND LOWER(r.city) = LOWER(m.city)")
    List<Restaurant> findNearbyRestaurants(Integer memberId);
}
