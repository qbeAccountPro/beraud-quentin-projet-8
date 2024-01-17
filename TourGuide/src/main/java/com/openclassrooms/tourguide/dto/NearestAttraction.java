package com.openclassrooms.tourguide.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import gpsUtil.location.Attraction;

/**
 * 
 * Some javadoc :
 * 
 * This class represent a nearest attraction. It owns the reward points and
 * the distance from the current user.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NearestAttraction {
  Attraction attraction;
  int rewardPoint;
  double distanceFromCurrentUser;
}
