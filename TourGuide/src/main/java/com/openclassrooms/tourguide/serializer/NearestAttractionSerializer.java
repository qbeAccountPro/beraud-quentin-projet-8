package com.openclassrooms.tourguide.serializer;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.openclassrooms.tourguide.dto.FiveNearbyAttractions;
import com.openclassrooms.tourguide.dto.NearestAttraction;


public class NearestAttractionSerializer extends StdSerializer<FiveNearbyAttractions> {

  public NearestAttractionSerializer(Class<FiveNearbyAttractions> t) {
    super(t);
  }

  @Override
  public void serialize(FiveNearbyAttractions value, JsonGenerator gen, SerializerProvider provider) {
    try {
      gen.writeStartObject();
      gen.writeStringField("UserLatitude", null);
      gen.writeStringField("UserLongitude", null);
      gen.writeFieldName("Attraction");

      for (NearestAttraction nearestAttraction : value.getNearestAttractions()) {
        gen.writeStartArray();
        serializeEach(nearestAttraction, gen, provider);
        gen.writeEndArray();

      }

      gen.writeEndObject();
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  public void serializeEach(NearestAttraction value, JsonGenerator gen, SerializerProvider provider) {
    try {
      gen.writeStartObject();
      gen.writeStringField("AttractionName", value.getAttraction().attractionName);
      gen.writeNumberField("AttractionLat", value.getAttraction().latitude);
      gen.writeNumberField("AttractionLon", value.getAttraction().longitude);
      gen.writeNumberField("DistanceUserAttraction", value.getDistanceFromCurrentUser());
      gen.writeNumberField("RewardPoints", value.getRewardPoint());
      gen.writeEndObject();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
