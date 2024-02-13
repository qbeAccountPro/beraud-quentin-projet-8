package com.openclassrooms.tourguide;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.tourguide.dto.FiveNearbyAttractions;
import com.openclassrooms.tourguide.service.RewardsService;
import com.openclassrooms.tourguide.service.TourGuideService;
import com.openclassrooms.tourguide.user.User;
import com.openclassrooms.tourguide.user.UserReward;
import java.util.UUID;

import gpsUtil.GpsUtil;
import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;

@SpringBootTest
public class TourGuideControllerTests {

	@Autowired
	TourGuideService tourGuideService;
	@Autowired
	RewardsService rewardsService;
	@Autowired
	TourGuideController tourGuideController;

	private MockMvc mvc;
	private static User user;
	private static VisitedLocation visitedLocation;
	private static Attraction attraction;

	@BeforeAll
	public static void testSetUp() {
		GpsUtil gpsUtil = new GpsUtil();
		user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
		visitedLocation = new VisitedLocation(user.getUserId(), gpsUtil.getAttractions().get(0),
				new Date());
		attraction = (Attraction) visitedLocation.location;
		user.addToVisitedLocations(visitedLocation);

	}

	public void setUpUser() {
		if (tourGuideService.getUser(user.getUserName()) == null) {
			rewardsService.calculateRewards(user);
			tourGuideService.addUser(user);
		}
	}

	@Test
	public void testGetLocation() throws Exception {
		// Arange
		setUpUser();
		String expectedString = "{\"userId\":\"" + user.getUserId()
				+ "\",\"location\":{\"longitude\":" + visitedLocation.location.longitude
				+ ",\"latitude\":" + visitedLocation.location.latitude
				+ ",\"attractionName\":\"" + attraction.attractionName
				+ "\",\"city\":\"" + attraction.city + "\",\"state\":\"" + attraction.state
				+ "\",\"attractionId\":\"" + attraction.attractionId
				+ "\"},\"timeVisited\":" + visitedLocation.timeVisited.getTime() + "}";

		// Act
		this.mvc = MockMvcBuilders
				.standaloneSetup(tourGuideController)
				.setControllerAdvice()
				.build();
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/getLocation")
				.param("userName", user.getUserName()))
				.andExpect(status().isOk())
				.andReturn();
		String resultString = result.getResponse().getContentAsString();

		// Assert
		assertEquals(expectedString, resultString);
	}

	@Test
	public void testGetRewards() throws Exception {
		// Arrange
		setUpUser();
		UserReward reward = user.getUserRewards().get(0);
		String exepectedContent = "[{\"visitedLocation\":"
				+ "{\"userId\":\"" + user.getUserId()
				+ "\",\"location\":{\"longitude\":" + user.getLastVisitedLocation().location.longitude
				+ ",\"latitude\":" + user.getLastVisitedLocation().location.latitude
				+ ",\"attractionName\":\"" + reward.attraction.attractionName
				+ "\",\"city\":\"" + reward.attraction.city
				+ "\",\"state\":\"" + reward.attraction.state
				+ "\",\"attractionId\":\"" + attraction.attractionId
				+ "\"},\"timeVisited\":" + user.getLastVisitedLocation().timeVisited.getTime()
				+ "},\"attraction\":{\"longitude\":" + reward.attraction.longitude
				+ ",\"latitude\":" + reward.attraction.latitude
				+ ",\"attractionName\":\"" + reward.attraction.attractionName
				+ "\",\"city\":\"" + reward.attraction.city
				+ "\",\"state\":\"" + reward.attraction.state
				+ "\",\"attractionId\":\"" + reward.attraction.attractionId
				+ "\"},\"rewardPoints\":" + reward.getRewardPoints() + "}]";

		// Act
		this.mvc = MockMvcBuilders
				.standaloneSetup(tourGuideController)
				.setControllerAdvice()
				.build();
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/getRewards")
				.param("userName", user.getUserName()))
				.andExpect(status().isOk())
				.andReturn();

		// Assert
		assertEquals(exepectedContent, result.getResponse().getContentAsString());
	}

	@Test
	public void testGetTripDeals() throws Exception {
		// Arrange
		setUpUser();

		// Act
		this.mvc = MockMvcBuilders
				.standaloneSetup(tourGuideController)
				.setControllerAdvice()
				.build();
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/getTripDeals")
				.param("userName", user.getUserName()))
				.andExpect(status().isOk())
				.andReturn();
		String contentResult = result.getResponse().getContentAsString();

		// Assert
		assertNotNull(contentResult);
	}

	@Test
	public void testGetFiveNearbyAttractions() throws Exception {
		// Arrange
		setUpUser();

		// Act
		this.mvc = MockMvcBuilders
				.standaloneSetup(tourGuideController)
				.setControllerAdvice()
				.build();
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/getNearbyAttractions")
				.param("userName", user.getUserName()))
				.andExpect(status().isOk())
				.andReturn();
		String content = result.getResponse().getContentAsString();

		// Deserialize
		ObjectMapper objectMapper = new ObjectMapper();
		FiveNearbyAttractions nearbyAttractions = objectMapper.readValue(content, FiveNearbyAttractions.class);

		// Assert
		assertTrue(nearbyAttractions.getNearestAttractions().size() == 5);
		assertNotNull(nearbyAttractions.getLatitudeUser());
		assertNotNull(nearbyAttractions.getLongitudeUser());
	}

	@Test
	public void testIndex() throws Exception {
		// Arrange
		this.mvc = MockMvcBuilders
				.standaloneSetup(tourGuideController)
				.setControllerAdvice()
				.build();

		User user = new User(UUID.randomUUID(), "bron", "000", "bron@tourGuide.com");
		tourGuideService.addUser(user);
		String expectedResult = "Greetings from TourGuide!";

		// Act
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/")
				.param("userName", user.getUserName()))
				.andExpect(status().isOk())
				.andReturn();
		String contentResult = result.getResponse().getContentAsString();

		// Assert
		assertEquals(expectedResult, contentResult);
	}
}