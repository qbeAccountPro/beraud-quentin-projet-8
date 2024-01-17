package com.openclassrooms.tourguide.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.openclassrooms.tourguide.dto.FiveNearbyAttractions;
import com.openclassrooms.tourguide.logging.EndpointsLogger;
import com.openclassrooms.tourguide.serializer.NearestAttractionSerializer;

@Service
public class Serialization {
  private ObjectMapper mapper;
  private SimpleModule module;
  private EndpointsLogger log = new EndpointsLogger();

  public ResponseEntity<ObjectNode> nearbyAttractionsSerialization(FiveNearbyAttractions FiveNA, String methodName) {
    NearestAttractionSerializer nearestAttractionSerializer = new NearestAttractionSerializer(
        FiveNearbyAttractions.class);

    mapper = new ObjectMapper();
    mapper.enable(SerializationFeature.INDENT_OUTPUT);
    module = new SimpleModule();
    module.addSerializer(FiveNearbyAttractions.class, nearestAttractionSerializer);
    mapper.registerModule(module);
    try {

      ObjectNode mainObject = mapper.createObjectNode();
      ArrayNode nearbyAttractionArray = mapper.valueToTree(FiveNA); // TODO : RESTART HERE 
      mainObject.set("FiveNearbyAttractions", nearbyAttractionArray);

      return log.successfullyGenerated(methodName, mainObject);
    } catch (Exception e) {
      return log.threwAnException(methodName);
    }
  }
}
