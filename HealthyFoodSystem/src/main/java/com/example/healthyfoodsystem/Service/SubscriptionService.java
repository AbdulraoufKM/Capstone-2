package com.example.healthyfoodsystem.Service;

import com.example.healthyfoodsystem.Model.Member;
import com.example.healthyfoodsystem.Model.Plan;
import com.example.healthyfoodsystem.Model.Subscription;
import com.example.healthyfoodsystem.Repository.MealRepository;
import com.example.healthyfoodsystem.Repository.MemberRepository;
import com.example.healthyfoodsystem.Repository.PlanRepository;
import com.example.healthyfoodsystem.Repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final PlanRepository planRepository;
    private final MemberRepository memberRepository;

    public List<Subscription> getAllSubscriptions() {
        LocalDate today = LocalDate.now();
        List<Subscription> subscriptions = subscriptionRepository.findAll();

        for (Subscription sub : subscriptions) {
            if (sub.getEndDate().isBefore(today) && !sub.getStatus().equalsIgnoreCase("NotActive")) {
                sub.setStatus("NotActive");
                subscriptionRepository.save(sub);
            }
        }
        return subscriptionRepository.findAll();


    }

    // 1  Register a new subscription
    // 2 (with loyalty discount check)

    public Boolean registerSubscription(Subscription subscription) {
        List<Subscription> subs = subscriptionRepository.findAll();
        for (Subscription sub : subs) {
            if (sub.getMemberId().equals(subscription.getMemberId()) && sub.getPlanId().equals(subscription.getPlanId()) && sub.getStatus().equalsIgnoreCase("Active") && sub.getEndDate().isAfter(LocalDate.now())) {
                return false;
            }
        }


        subscription.setStartDate(LocalDate.now());


        switch (subscription.getType())
        {
            case "Daily" -> subscription.setEndDate(LocalDate.now().plusDays(1));

            case "Weekly" -> subscription.setEndDate(LocalDate.now().plusWeeks(1));

            case "Monthly" -> subscription.setEndDate(LocalDate.now().plusMonths(1));

            case "Yearly" -> subscription.setEndDate(LocalDate.now().plusYears(1));

            default -> {
                return false;
            }
        }


        subscription.setStatus("Active");

        Plan plan = planRepository.findPlanById(subscription.getPlanId());
        if (plan == null) {
            return false;
        }

        double finalPrice = plan.getPrice();

        //loyalty discount
        for (Subscription sub : subs) {
            if (sub.getMemberId().equals(subscription.getMemberId()) && sub.getType().equalsIgnoreCase("Yearly") && (sub.getStatus().equalsIgnoreCase("Active") || sub.getStatus().equalsIgnoreCase("Canceled"))) {
                finalPrice = finalPrice * 0.8; // apply 20% discount
                break;
            }
        }


        subscription.setPrice(finalPrice);

        subscriptionRepository.save(subscription);
        return true;
    }




    public Boolean updateSubscription(Integer id, Subscription subscription) {
        Subscription oldSubscription = subscriptionRepository.findSubscriptionById(id);

        if (oldSubscription == null) {
            return false;
        }

        oldSubscription.setType(subscription.getType());
        LocalDate today = LocalDate.now();
        List<Subscription> subs = subscriptionRepository.findAll();


        for (Subscription sub : subs) {
            if (sub.getEndDate().isBefore(today) && !sub.getStatus().equalsIgnoreCase("NotActive")) {
                sub.setStatus("NotActive");
                subscriptionRepository.save(sub);
            }
        }
        oldSubscription.setStatus(subscription.getStatus());
        oldSubscription.setPlanId(subscription.getPlanId());
        oldSubscription.setRatingId(subscription.getRatingId());

        subscriptionRepository.save(oldSubscription);
        return true;
    }

    public Boolean deleteSubscription(Integer id) {
        Subscription subscription = subscriptionRepository.findSubscriptionById(id);
        if (subscription == null) {
            return false;
        }

        subscriptionRepository.delete(subscription);
        return true;
    }

    // 3 Get all subscriptions for restaurant's plans with members info
    public List<Subscription> SubsMemberByRestaurant(Integer restaurantId) {
        List<Plan> plans = planRepository.findPlanByRestaurantId(restaurantId);
        List<Subscription> result = new ArrayList<>();

        for (Plan plan : plans) {
            List<Subscription> subs = subscriptionRepository.findAllByPlanId(plan.getId());

            for (Subscription sub : subs) {
                Member member = memberRepository.findMemberById(sub.getMemberId());
                sub.setMember(member);
                result.add(sub);
            }
        }

        return result;
    }

    // 4 Cancel a subscription
    public Boolean cancelSubscription(Integer subscriptionId) {
        Subscription subscription = subscriptionRepository.findSubscriptionById(subscriptionId);
        if (subscription == null || !subscription.getStatus().equalsIgnoreCase("Active")) {
            return false;
        }

        subscription.setStatus("Canceled");
        subscriptionRepository.save(subscription);
        return true;
    }

    // 5 Extend a subscription
    public Boolean extendSubscription(Integer id) {
        Subscription subscription = subscriptionRepository.findSubscriptionById(id);
        if (subscription == null || !subscription.getStatus().equalsIgnoreCase("Active")) {
            return false;
        }

        switch (subscription.getType()) {
            case "Daily" -> subscription.setEndDate(subscription.getEndDate().plusDays(1));
            case "Weekly" -> subscription.setEndDate(subscription.getEndDate().plusWeeks(1));
            case "Monthly" -> subscription.setEndDate(subscription.getEndDate().plusMonths(1));
            case "Yearly" -> subscription.setEndDate(subscription.getEndDate().plusYears(1));
            default -> {
                return false;
            }
        }

        subscriptionRepository.save(subscription);
        return true;
    }

    // 6 Resubscribe to a previously canceled or expired subscription
    public Boolean resubscribeToPlan(Integer oldSubId) {
        Subscription oldSub = subscriptionRepository.findSubscriptionById(oldSubId);
        if (oldSub == null || oldSub.getStatus().equalsIgnoreCase("Active")) {
            return false;
        }

        Subscription newSub = new Subscription();

        newSub.setMemberId(oldSub.getMemberId());
        newSub.setPlanId(oldSub.getPlanId());
        newSub.setType(oldSub.getType());
        newSub.setStartDate(LocalDate.now());

        switch (oldSub.getType()) {
            case "Daily" -> newSub.setEndDate(LocalDate.now().plusDays(1));
            case "Weekly" -> newSub.setEndDate(LocalDate.now().plusWeeks(1));
            case "Monthly" -> newSub.setEndDate(LocalDate.now().plusMonths(1));
            case "Yearly" -> newSub.setEndDate(LocalDate.now().plusYears(1));
            default -> {
                return false;
            }
        }

        newSub.setStatus("Active");

        Plan plan = planRepository.findPlanById(oldSub.getPlanId());
        if (plan == null) return false;

        double finalPrice = plan.getPrice();

        // Apply 20% discount if user had yearly sub before
        List<Subscription> allSubs = subscriptionRepository.findAll();
        for (Subscription s : allSubs) {
            if (s.getMemberId().equals(newSub.getMemberId()) && s.getType().equalsIgnoreCase("Yearly")) {
                finalPrice *= 0.8;
                break;
            }
        }

        newSub.setPrice(finalPrice);
        subscriptionRepository.save(newSub);
        return true;
    }









}
