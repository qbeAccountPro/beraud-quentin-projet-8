package com.openclassrooms.tourguide;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.openclassrooms.tourguide.service.RewardsService;

import gpsUtil.GpsUtil;
import rewardCentral.RewardCentral;

@Configuration

/**
 * Some Javadoc :
 * 
 * Configures and initializes internal and external services for the TourGuide
 * application.
 * 
 */
public class TourGuideModule {

	/**
	 * Some Javadoc :
	 * 
	 * Creates and returns a new instance of the GpsUtil service.
	 * 
	 * @return The GpsUtil service instance.
	 */
	@Bean
	public GpsUtil getGpsUtil() {
		return new GpsUtil();
	}

	/**
	 * Some Javadoc :
	 * 
	 * Creates and returns a new instance of the RewardsService using the GpsUtil
	 * and RewardCentral services.
	 * 
	 * @return The RewardsService instance.
	 */
	@Bean
	public RewardsService getRewardsService() {
		return new RewardsService(getGpsUtil(), getRewardCentral());
	}

	/**
	 * Some Javadoc :
	 * 
	 * Creates and returns a new instance of the RewardCentral service.
	 * 
	 * @return The RewardCentral service instance.
	 */
	@Bean
	public RewardCentral getRewardCentral() {
		return new RewardCentral();
	}

}