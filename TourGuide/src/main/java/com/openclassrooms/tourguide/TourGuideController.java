package com.openclassrooms.tourguide;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.tourguide.dto.FiveNearbyAttractions;
import com.openclassrooms.tourguide.service.TourGuideService;
import com.openclassrooms.tourguide.user.User;
import com.openclassrooms.tourguide.user.UserReward;

import gpsUtil.location.VisitedLocation;
import tripPricer.Provider;

/**
 * Some Javadoc :
 * 
 * REST controller for handling TourGuide functionalities such as retrieving
 * user
 * locations, nearby attractions, rewards, and trip deals.
 */
@RestController
public class TourGuideController {

    @Autowired
    TourGuideService tourGuideService;

    /**
     * Some Javadoc :
     * 
     * Handles requests to the root URL.
     *
     * @return A greeting message from TourGuide.
     */
    @RequestMapping("/")
    public String index() {
        return "Greetings from TourGuide!";
    }

    /**
     * Some Javadoc :
     *
     * Retrieves the visited location of a user.
     *
     * @param userName The username of the user.
     * @return The visited location of the user.
     */
    @RequestMapping("/getLocation")
    public VisitedLocation getLocation(@RequestParam String userName) {
        return tourGuideService.getUserLocation(getUser(userName));
    }

    /**
     * Some Javadoc :
     *
     * Retrieves the five nearby attractions along with their details and reward
     * points.
     *
     * @param userName The username of the user.
     * @return An DTO containing information about the five nearby attractions.
     */
    @RequestMapping("/getNearbyAttractions")
    public FiveNearbyAttractions getNearbyAttractions(@RequestParam String userName) {
        return tourGuideService
                .getFiveNearbyAttractionsWithRewardsPoint(userName);
        // TODO CHECK DISTANT VALUE IS 0 BUT THE RETURN WORKS
        // TODO check Attraction model car il y à des infos superflu renvoyer créer un
        // DTO en plus

    }

    /**
     * Some Javadoc :
     *
     * Retrieves the rewards earned by a user.
     *
     * @param userName The username of the user.
     * @return A list of rewards earned by the user.
     */
    @RequestMapping("/getRewards")
    public List<UserReward> getRewards(@RequestParam String userName) {
        return tourGuideService.getUserRewards(getUser(userName));
    }

    /**
     * Some Javadoc :
     *
     * Retrieves trip deals for a user.
     *
     * @param userName The username of the user.
     * @return A list of trip deals available for the user.
     */
    @RequestMapping("/getTripDeals")
    public List<Provider> getTripDeals(@RequestParam String userName) {
        return tourGuideService.getTripDeals(getUser(userName));
    }

    /**
     * Some Javadoc :
     *
     * Retrieves the user object associated with the specified username.
     *
     * @param userName The username of the user.
     * @return The user object.
     */
    private User getUser(String userName) {
        return tourGuideService.getUser(userName);
    }
}