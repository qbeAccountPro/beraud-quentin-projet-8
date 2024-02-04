package com.openclassrooms.tourguide.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 
 * Some javadoc :
 * 
 * This class represent a nearest attraction. It owns the reward points and
 * the distance from the current user.
 */
@Data
@AllArgsConstructor
public class NearestAttraction {
  double latitude;
  double longitude;
  String attractionName;
  int rewardPoint;
  double distanceFromCurrentUser;
}
