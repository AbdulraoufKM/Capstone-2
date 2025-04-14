package com.example.healthyfoodsystem.Service;

import com.example.healthyfoodsystem.Model.Rating;
import com.example.healthyfoodsystem.Model.Subscription;
import com.example.healthyfoodsystem.Repository.RatingRepository;
import com.example.healthyfoodsystem.Repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingService {

    private final RatingRepository ratingRepository;
    private final SubscriptionRepository subscriptionRepository;

    public List<Rating> getAllRatings() {
        return ratingRepository.findAll();
    }

    public Boolean addRating(Rating rating) {
        Subscription subscription = subscriptionRepository.findSubscriptionById(rating.getSubscriptionId());

        if (subscription == null || !subscription.getMemberId().equals(rating.getMemberId())) {
            return false;
        }

        rating.setCreatedAt(LocalDate.now());
        ratingRepository.save(rating);
        return true;
    }

    public Boolean updateRating(Integer id, Rating rating) {
        Rating oldRating = ratingRepository.findRatingById(id);

        if (oldRating == null) {
            return false;
        }

        oldRating.setStars(rating.getStars());
        oldRating.setComment(rating.getComment());
        ratingRepository.save(oldRating);

        return true;
    }

    public Boolean deleteRating(Integer id) {
        Rating rating = ratingRepository.findRatingById(id);

        if (rating == null) {
            return false;
        }

        ratingRepository.delete(rating);
        return true;
    }
}
