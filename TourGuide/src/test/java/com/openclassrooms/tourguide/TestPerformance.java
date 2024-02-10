package com.openclassrooms.tourguide;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.StopWatch;
import org.junit.jupiter.api.Test;

import gpsUtil.GpsUtil;
import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;
import rewardCentral.RewardCentral;
import com.openclassrooms.tourguide.helper.InternalTestHelper;
import com.openclassrooms.tourguide.service.RewardsService;
import com.openclassrooms.tourguide.service.TourGuideService;
import com.openclassrooms.tourguide.user.User;

public class TestPerformance {

	private int setInternalUserNumberTest = 10000;

	@Test
	public void highVolumeTrackLocation() throws InterruptedException, ExecutionException {
		GpsUtil gpsUtil = new GpsUtil();
		RewardsService rewardsService = new RewardsService(gpsUtil, new RewardCentral());
		RewardCentral rewardCentral = new RewardCentral();
		InternalTestHelper.setInternalUserNumber(setInternalUserNumberTest);
		TourGuideService tourGuideService = new TourGuideService(gpsUtil, rewardsService, rewardCentral);
		List<User> users = tourGuideService.getAllUsers();
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		List<CompletableFuture<VisitedLocation>> futures = new ArrayList<>();

		for (User user : users) {
			futures.add(tourGuideService.trackUserLocation(user));
		}
		CompletableFuture<Void> combinedFuture = CompletableFuture
				.allOf(futures.toArray(new CompletableFuture[0]));
		combinedFuture.join();
		stopWatch.stop();
		tourGuideService.tracker.stopTracking();
		System.out.println("highVolumeTrackLocation: Time Elapsed: "
				+ TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds.");
	}

	@Test
	public void highVolumeGetRewards() throws InterruptedException, ExecutionException {
		GpsUtil gpsUtil = new GpsUtil();
		RewardsService rewardsService = new RewardsService(gpsUtil, new RewardCentral());
		RewardCentral rewardCentral = new RewardCentral();

		InternalTestHelper.setInternalUserNumber(setInternalUserNumberTest);
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		TourGuideService tourGuideService = new TourGuideService(gpsUtil, rewardsService, rewardCentral);

		Attraction attraction = gpsUtil.getAttractions().get(0);
		List<User> users = tourGuideService.getAllUsers();

		users.forEach(u -> u.addToVisitedLocations(new VisitedLocation(u.getUserId(), attraction, new Date())));

		List<CompletableFuture<Void>> futures = new ArrayList<>();
		users.forEach(u -> futures.add(rewardsService.calculateRewards(u)));
		CompletableFuture<Void> combinedFuture = CompletableFuture
				.allOf(futures.toArray(new CompletableFuture[0]));
		combinedFuture.join();

		users.forEach(u -> assertTrue(u.getUserRewards().size() > 0));

		stopWatch.stop();
		tourGuideService.tracker.stopTracking();

		System.out.println("highVolumeGetRewards: Time Elapsed: " + TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime())
				+ " seconds.");
		assertTrue(TimeUnit.MINUTES.toSeconds(20) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
	}

}