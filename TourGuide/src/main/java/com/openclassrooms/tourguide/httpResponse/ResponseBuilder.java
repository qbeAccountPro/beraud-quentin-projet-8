package com.openclassrooms.tourguide.httpResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Some javadoc.
 * 
 * Builds consistent HTTP responses for various scenarios.
 * 
 * This class provides methods to generate ResponseEntity instances
 * with appropriate status codes and response bodies.
 */
public class ResponseBuilder {

  /**
   * Some javadoc.
   * 
   * Builds a response for successful content addition.
   * 
   * @return A ResponseEntity with a "Content added successfully" message and
   *         status code CREATED.
   */
  public ResponseEntity<String> addedSuccessfully() {
    return ResponseEntity.status(HttpStatus.CREATED).body("Content added successfully.");
  }

  /**
   * Some javadoc.
   * 
   * Builds a response for incorrect content.
   * 
   * @return A ResponseEntity with an "Incorrect content" message and status code
   *         BAD_REQUEST.
   */
  public ResponseEntity<String> incorrectContent() {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect content.");
  }

  /**
   * Some javadoc.
   * 
   * Builds a response for a request argument with no match.
   * 
   * @return A ResponseEntity with an "Argument has no match" message and status
   *         code NOT_FOUND.
   */
  public ResponseEntity<String> hasNoMatch() {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Argument has no match.");
  }

  /**
   * Some javadoc.
   * 
   * Builds a response for successful content update.
   * 
   * @return A ResponseEntity with a "Content updated successfully" message and
   *         status code OK.
   */
  public ResponseEntity<String> updatedSuccessfully() {
    return ResponseEntity.status(HttpStatus.OK).body("Content updated successfully.");
  }

  /**
   * Some javadoc.
   * 
   * Builds a response for a request that threw an exception.
   * 
   * @return A ResponseEntity with a "Request threw an exception" message and
   *         status code BAD_REQUEST.
   */
  public ResponseEntity<ObjectNode> threwAnException() {
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode responseJson = mapper.createObjectNode();

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseJson);

  }

  /**
   * Some javadoc.
   * 
   * Builds a response for successful content deletion.
   * 
   * @return A ResponseEntity with a "Content deleted successfully" message and
   *         status code OK.
   */
  public ResponseEntity<String> deletedSuccessfully() {
    return ResponseEntity.status(HttpStatus.OK).body("Content deleted successfully.");
  }

  /**
   * Some javadoc.
   * 
   * Builds a response for an empty request answer.
   * 
   * @return A ResponseEntity with a "Request generated an empty answer" message
   *         and
   *         status code OK.
   */
  public ResponseEntity<ObjectNode> emptyAnswer() {
    return ResponseEntity.ok(null);
  }

  /**
   * Some javadoc.
   * 
   * Builds a response for an successfully generated request.
   * 
   * @return A ResponseEntity with a "Successfully generated" message
   *         and
   *         status code OK.
   */
  public ResponseEntity<ObjectNode> successfullyGenerated(ObjectNode mainObject) {
    return ResponseEntity.ok(mainObject);
  }

  /**
   * Some javadoc.
   * 
   * Builds a response when a mapping already exists.
   * 
   * @return A ResponseEntity with a "Mapping already exists" message
   *         and status code CONFLICT (409).
   */
  public ResponseEntity<String> ExistingMapping() {
    return ResponseEntity.status(HttpStatus.CONFLICT).body("Mapping already exist.");
  }
}