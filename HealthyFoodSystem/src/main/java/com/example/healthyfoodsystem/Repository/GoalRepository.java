package com.example.healthyfoodsystem.Repository;

import com.example.healthyfoodsystem.Model.Goal;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoalRepository extends JpaRepository<Goal,Integer> {

    Goal findByMemberId(Integer memberId);

}
