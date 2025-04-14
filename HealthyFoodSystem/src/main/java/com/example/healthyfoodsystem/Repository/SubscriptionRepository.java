package com.example.healthyfoodsystem.Repository;

import com.example.healthyfoodsystem.Model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription,Integer> {

    Subscription findSubscriptionById(Integer id);
    List<Subscription> findAllByPlanId(Integer planId);


    @Query("SELECT COUNT(s) FROM Subscription s WHERE s.planId = ?1")
    Integer countByPlanId(Integer planId);

    Subscription findSubscriptionByMemberIdAndStatus( Integer memberId, String status);
}

