package com.example.healthyfoodsystem.Repository;

import com.example.healthyfoodsystem.Model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<Rating,Integer> {

    Rating findRatingById(Integer id);
}
