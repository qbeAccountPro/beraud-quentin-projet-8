package com.openclassrooms.tourguide.service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.stereotype.Service;

import gpsUtil.GpsUtil;
import gpsUtil.location.Attraction;
import gpsUtil.location.Location;
import gpsUtil.location.VisitedLocation;
import rewardCentral.RewardCentral;
import com.openclassrooms.tourguide.user.User;
import com.openclassrooms.tourguide.user.UserReward;

@Service
public class RewardsService {
	private static final double STATUTE_MILES_PER_NAUTICAL_MILE = 1.15077945; // TODO quest ce pourquoi ne pas le réalise
																																						// en métric au lieu de passer de miles
																																						// nautique a surfacien?

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

	public synchronized void calculateRewards(User user) {
		Lock verrou = new ReentrantLock();
		verrou.lock();
		try {
			System.out.println("Lock");
			List<VisitedLocation> userLocations = user.getVisitedLocations();
			CopyOnWriteArrayList<VisitedLocation> userLocationsCoW = new CopyOnWriteArrayList<>(userLocations);
			List<Attraction> attractions = gpsUtil.getAttractions();

			System.out.println("Size user location :" + userLocations.size());
			System.out.println("Size user cow location :" + userLocationsCoW.size());
			System.out.println("Size attractions :" + attractions.size());

			for (VisitedLocation visitedLocation : userLocationsCoW) {
				for (Attraction attraction : attractions) {
					List<UserReward> userRewards = user.getUserRewards();
					System.out.println("UserReward =" + userRewards.size());
					if (user.getUserRewards().stream().filter(r -> r.attraction.attractionName.equals(attraction.attractionName))
							.count() == 0) {
						if (nearAttraction(visitedLocation, attraction)) {
							System.out.println("+1");
							user.addUserReward(new UserReward(visitedLocation, attraction,
									getRewardPoints(attraction, user)));
						}
					}
				}
			}
		} finally {
			verrou.unlock();
		}
	}

	public boolean isWithinAttractionProximity(Attraction attraction, Location location) {
		return getDistance(attraction, location) > attractionProximityRange ? false : true;
	}

	private synchronized boolean nearAttraction(VisitedLocation visitedLocation, Attraction attraction) {
		return getDistance(attraction, visitedLocation.location) > proximityBuffer ? false : true;
	}

	private synchronized int getRewardPoints(Attraction attraction, User user) {
		Lock verrou = new ReentrantLock();
		verrou.lock();
		try {
			return rewardsCentral.getAttractionRewardPoints(attraction.attractionId, user.getUserId());
		} finally {
			verrou.unlock();
		}
	}

	public synchronized double getDistance(Location loc1, Location loc2) {
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
