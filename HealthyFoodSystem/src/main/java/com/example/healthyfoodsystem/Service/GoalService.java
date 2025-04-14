package com.example.healthyfoodsystem.Service;

import com.example.healthyfoodsystem.Model.Goal;
import com.example.healthyfoodsystem.Model.Member;
import com.example.healthyfoodsystem.Repository.GoalRepository;
import com.example.healthyfoodsystem.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GoalService {

    private final GoalRepository goalRepository;
    private final MemberRepository memberRepository;

    public boolean addGoal(Goal goal) {
        Member member = memberRepository.findMemberById(goal.getMemberId());
        if (member == null) {
            return false;
        }

        Goal existing = goalRepository.findByMemberId(goal.getMemberId());
        if (existing != null) {
            return false;
        }

        goalRepository.save(goal);
        return true;
    }

    public boolean updateGoal(Integer id, Goal goal) {
        Goal oldGoal = goalRepository.findById(id).orElse(null);
        if (oldGoal == null) return false;

        oldGoal.setName(goal.getName());
        oldGoal.setTargetWeight(goal.getTargetWeight());
        oldGoal.setExcludedFoods(goal.getExcludedFoods());
        oldGoal.setMemberId(goal.getMemberId());

        goalRepository.save(oldGoal);
        return true;
    }

    public boolean deleteGoalByMemberId(Integer memberId) {
        Goal goal = goalRepository.findByMemberId(memberId);
        if (goal == null) return false;
        goalRepository.delete(goal);
        return true;
    }



    // 15 Calculate recommended calories
    public Double calculateRecommendedCalories(Goal goal) {
        Member member = memberRepository.findMemberById(goal.getMemberId());
        if (member == null) {
            return null;
        }

        double weight = member.getWeight(); // in kg
        double height = member.getHeight(); // in cm
        int age = member.getAge();
        String gender = member.getGender();

        double bmr;

        if (gender.equalsIgnoreCase("male")) {
            bmr = 10 * ( weight) +6.25 *(height) -5 * (age)+5;
            bmr *= 1.55;
        } else if (gender.equalsIgnoreCase("female")) {
            bmr = 10 * ( weight) +6.25 *(height) -5 * (age) -161;
            bmr = 1.55;
        } else {
            return null;
        }

        double targetWeight = goal.getTargetWeight();

        if (targetWeight < weight) {
            return bmr - 500; // weight loss
        } else if (targetWeight > weight) {
            return bmr + 300; // muscle gain
        } else {
            return bmr; // balance
        }
    }

    // 21 (extra) Get goal by member ID
    public Goal getGoalByMemberId(Integer memberId) {
        return goalRepository.findByMemberId(memberId);
    }


}
