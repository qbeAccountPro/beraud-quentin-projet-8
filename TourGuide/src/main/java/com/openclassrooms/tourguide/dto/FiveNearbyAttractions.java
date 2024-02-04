package com.openclassrooms.tourguide.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Some javadoc :
 * 
 * This class represent a data transfer object. It regroup the five nearest
 * attractions from the current user and add for each attraction the reward
 * points can be gathered from the RewardsCentral and the distance to the user
 * in the nearestAttraction.
 * 
 */
@Data
@AllArgsConstructor
public class FiveNearbyAttractions {
  double latitudeUser;
  double longitudeUser;
  List<NearestAttraction> nearestAttractions;
}
