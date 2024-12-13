package com.openclassrooms.tourguide.controller;

import com.google.gson.Gson;
import com.openclassrooms.tourguide.dto.NearByAttractionDto;
import com.openclassrooms.tourguide.service.RewardsService;
import com.openclassrooms.tourguide.service.TourGuideService;
import com.openclassrooms.tourguide.user.User;
import com.openclassrooms.tourguide.user.UserReward;
import gpsUtil.location.Attraction;
import gpsUtil.location.Location;
import gpsUtil.location.VisitedLocation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rewardCentral.RewardCentral;
import tripPricer.Provider;

import java.util.List;

@RestController
public class TourGuideController {


    private final TourGuideService tourGuideService;
    private final RewardCentral rewardCentral;
    private final RewardsService rewardsService;

    public TourGuideController(TourGuideService tourGuideService, RewardCentral rewardCentral, RewardsService rewardsService) {
        this.tourGuideService = tourGuideService;
        this.rewardCentral = rewardCentral;
        this.rewardsService = rewardsService;
    }

    @RequestMapping("/")
    public String index() {
        return "Greetings from TourGuide!";
    }

    @RequestMapping("/getLocation")
    public VisitedLocation getLocation(@RequestParam String userName) {
        return tourGuideService.getUserLocation(getUser(userName));
    }


    @RequestMapping("/getNearbyAttractions")
    public String getNearbyAttractions(@RequestParam String userName) {
        VisitedLocation visitedLocation = tourGuideService.getUserLocation(getUser(userName));

        List<Attraction> attractions = tourGuideService.getNearByAttractions(visitedLocation);

        return new Gson().toJson(
                attractions
                        .stream()
                        .map(attraction -> new NearByAttractionDto(
                                attraction.attractionName,
                                new Location(attraction.latitude, attraction.longitude),
                                visitedLocation.location,
                                rewardsService.getDistance(attraction, visitedLocation.location),
                                rewardCentral.getAttractionRewardPoints(attraction.attractionId, getUser(userName).getUserId()))
                        )
                        .toList()
        );
    }

    @RequestMapping("/getRewards")
    public List<UserReward> getRewards(@RequestParam String userName) {
        return tourGuideService.getUserRewards(getUser(userName));
    }

    @RequestMapping("/getTripDeals")
    public List<Provider> getTripDeals(@RequestParam String userName) {
        return tourGuideService.getTripDeals(getUser(userName));
    }

    private User getUser(String userName) {
        return tourGuideService.getUser(userName);
    }


}