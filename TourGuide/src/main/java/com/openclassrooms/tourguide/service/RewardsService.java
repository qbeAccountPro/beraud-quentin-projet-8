package com.openclassrooms.tourguide.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.stereotype.Service;

import com.openclassrooms.tourguide.user.User;
import com.openclassrooms.tourguide.user.UserReward;

import gpsUtil.GpsUtil;
import gpsUtil.location.Attraction;
import gpsUtil.location.Location;
import gpsUtil.location.VisitedLocation;
import rewardCentral.RewardCentral;

/**
 * Some Javadoc :
 * 
 * The RewardsService class provides methods for calculating rewards for users.
 */
@Service
public class RewardsService {
	private static final double STATUTE_MILES_PER_NAUTICAL_MILE = 1.15077945;

	// proximity in miles
	private int defaultProximityBuffer = 10;
	private int proximityBuffer = defaultProximityBuffer;
	private int attractionProximityRange = 200;
	private final GpsUtil gpsUtil;
	private final RewardCentral rewardsCentral;

	public RewardsService(GpsUtil gpsUtil, RewardCentral rewardCentral) {
		this.gpsUtil = gpsUtil;
		this.rewardsCentral = rewardCentral;

	}

	public void setProximityBuffer(int proximityBuffer) {
		this.proximityBuffer = proximityBuffer;
	}

	public void setDefaultProximityBuffer() {
		proximityBuffer = defaultProximityBuffer;
	}

	/**
	 * Some Javadoc :
	 * 
	 * This method calculate the rewards for a specific user.
	 * It takes each visited location and compares them with each attraction.
	 * If an attraction is at least inside the nearestdistance value from the user
	 * visited location, the reward is assigned.
	 * 
	 */
	public CompletableFuture<Void> calculateRewards(User user) {
		List<VisitedLocation> visitedLocations = user.getVisitedLocations();
		List<CompletableFuture<Void>> futures = new ArrayList<>();
		CopyOnWriteArrayList<VisitedLocation> visitedLocationCoW = new CopyOnWriteArrayList<>(visitedLocations);

		ExecutorService executorService = Executors.newFixedThreadPool(50);

		CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
			for (Attraction attraction : gpsUtil.getAttractions()) {
				if (user.getUserRewards().stream()
						.noneMatch(r -> r.attraction.attractionName.equals(attraction.attractionName))) {
					for (VisitedLocation visitedLocation2 : visitedLocationCoW) {
						if (nearAttraction(visitedLocation2, attraction)) {
							user.addUserReward(new UserReward(visitedLocation2, attraction, getRewardPoints(attraction, user)));
						}
					}
				} else {
					break;
				}
			}
		}, executorService).whenComplete((result, throwable) -> {
			executorService.shutdown();
		});
		
		futures.add(future);

		CompletableFuture<Void> allFutures = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
		return allFutures;
	}

	public boolean isWithinAttractionProximity(Attraction attraction, Location location) {
		return getDistance(attraction, location) > attractionProximityRange ? false : true;
	}

	private boolean nearAttraction(VisitedLocation visitedLocation, Attraction attraction) {
		return getDistance(attraction, visitedLocation.location) > proximityBuffer ? false : true;
	}

	private int getRewardPoints(Attraction attraction, User user) {
		return rewardsCentral.getAttractionRewardPoints(attraction.attractionId, user.getUserId());
	}

	public double getDistance(Location loc1, Location loc2) {
		double lat1 = Math.toRadians(loc1.latitude);
		double lon1 = Math.toRadians(loc1.longitude);
		double lat2 = Math.toRadians(loc2.latitude);
		double lon2 = Math.toRadians(loc2.longitude);

		double angle = Math.acos(Math.sin(lat1) * Math.sin(lat2)
				+ Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));

		double nauticalMiles = 60 * Math.toDegrees(angle);

		double statuteMiles = STATUTE_MILES_PER_NAUTICAL_MILE * nauticalMiles;

		return statuteMiles;
	}

}