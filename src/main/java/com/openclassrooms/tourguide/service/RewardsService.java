package com.openclassrooms.tourguide.service;

import com.openclassrooms.tourguide.user.User;
import com.openclassrooms.tourguide.user.UserReward;
import gpsUtil.GpsUtil;
import gpsUtil.location.Attraction;
import gpsUtil.location.Location;
import gpsUtil.location.VisitedLocation;
import lombok.Setter;
import org.springframework.stereotype.Service;
import rewardCentral.RewardCentral;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class RewardsService {
    private static final double STATUTE_MILES_PER_NAUTICAL_MILE = 1.15077945;

    // proximity in miles
    private final static int attractionProximityRange = 200;
    private final static int defaultProximityBuffer = 10;

    private final GpsUtil gpsUtil;
    private final RewardCentral rewardsCentral;

    ExecutorService executor = Executors.newFixedThreadPool(100);

    @Setter
    private int proximityBuffer = 10;

    public RewardsService(GpsUtil gpsUtil, RewardCentral rewardCentral) {
        this.gpsUtil = gpsUtil;
        this.rewardsCentral = rewardCentral;
    }

    public CompletableFuture<Void> calculateRewards(User user) {
        return CompletableFuture.supplyAsync(() -> {
            CopyOnWriteArrayList<VisitedLocation> userLocations = new CopyOnWriteArrayList<>(user.getVisitedLocations());
            List<Attraction> attractions = gpsUtil.getAttractions();

            userLocations.forEach(visitedLocation -> {
                attractions
                        .stream()
                        .filter(attraction ->
                                user.getUserRewards()
                                        .stream()
                                        .noneMatch(userReward ->
                                                userReward.attraction.attractionName.equals(attraction.attractionName))
                                        && nearAttraction(visitedLocation, attraction))
                        .forEach(attraction -> {
                            UserReward newUserReward = new UserReward(visitedLocation, attraction, getRewardPoints(attraction, user));
                            user.addUserReward(newUserReward);
                        });
            });
            return null;
        }, executor);
    }

    public boolean isWithinAttractionProximity(Attraction attraction, Location location) {
        return getDistance(attraction, location) < attractionProximityRange;
    }

    private boolean nearAttraction(VisitedLocation visitedLocation, Attraction attraction) {
        return getDistance(attraction, visitedLocation.location) < proximityBuffer;
    }

    private int getRewardPoints(Attraction attraction, User user) {
        return rewardsCentral.getAttractionRewardPoints(attraction.attractionId, user.getUserId());
    }

    public double getDistance(Location loc1, Location loc2) {
        double lat1 = Math.toRadians(loc1.latitude);
        double lon1 = Math.toRadians(loc1.longitude);
        double lat2 = Math.toRadians(loc2.latitude);
        double lon2 = Math.toRadians(loc2.longitude);

        double angle = Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));

        double nauticalMiles = 60 * Math.toDegrees(angle);
        return STATUTE_MILES_PER_NAUTICAL_MILE * nauticalMiles;
    }
}
