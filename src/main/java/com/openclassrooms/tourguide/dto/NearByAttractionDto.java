package com.openclassrooms.tourguide.dto;

import gpsUtil.location.Location;

public record NearByAttractionDto(String name, Location attractionLocation, Location userLocation, double distance,
                                  double rewards) {
}
