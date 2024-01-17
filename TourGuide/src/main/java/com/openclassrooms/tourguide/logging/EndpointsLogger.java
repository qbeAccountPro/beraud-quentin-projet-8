package com.openclassrooms.tourguide.logging;

import org.springframework.http.ResponseEntity;
import org.tinylog.Logger;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.openclassrooms.tourguide.httpResponse.ResponseBuilder;

/* 
 * Some javadoc.
 * 
 * This class represent all log generated during the endpoint request.
 */
public class EndpointsLogger {

  private ResponseBuilder response = new ResponseBuilder();

  /**
   * Some javadoc.
   * 
   * Logs a request for the given method.
   * 
   * @param methodName The name of the method.
   */
  public void request(String methodName) {
    Logger.info("Request " + methodName + ".");
  }

  /**
   * Some javadoc.
   * 
   * Logs a request with an argument for the given method.
   * 
   * @param methodName The name of the method.
   * @param argument   The argument value.
   */
  public void request(String methodName, String argument) {
    Logger.info("Request " + methodName + " with this argument : " + argument + ".");
  }

  /**
   * Some javadoc.
   * 
   * Logs a request with two arguments for the given method.
   * 
   * @param methodName The name of the method.
   * @param argument1  The first argument value.
   * @param argument2  The second argument value.
   */
  public void request(String methodName, String argument1, String argument2) {
    Logger.info("Request " + methodName + " with this arguments : " + argument1 + " & " + argument2 + ".");
  }

  /**
   * Some javadoc.
   * 
   * Logs an incorrect content response.
   * 
   * @param methodName The name of the method.
   * @return A response indicating incorrect content.
   */
  public ResponseEntity<String> incorrectContent(String methodName) {
    Logger.info("Answer " + methodName + " : content is incorrect.");
    return response.incorrectContent();
  }

  /**
   * Some javadoc.
   * 
   * Logs an argument with no match response.
   * 
   * @param methodName The name of the method.
   * @return A response indicating no matching argument.
   */
  public ResponseEntity<String> argumentHasNoMatch(String methodName) {
    Logger.info("Answer " + methodName + " : argument has no match.");
    return response.hasNoMatch();
  }

  /**
   * Some javadoc.
   * 
   * Logs a successful content addition response.
   * 
   * @param methodName The name of the method.
   * @return A response indicating successful addition.
   */
  public ResponseEntity<String> addedSuccessfully(String methodName) {
    Logger.info("Answer " + methodName + " : content added successfully.");
    return response.addedSuccessfully();
  }

  /**
   * Some javadoc.
   * 
   * Logs a successful content update response.
   * 
   * @param methodName The name of the method.
   * @return A response indicating successful update.
   */
  public ResponseEntity<String> updatedSuccessfully(String methodName) {
    Logger.info("Answer " + methodName + " : content updated successfully.");
    return response.updatedSuccessfully();
  }

  /**
   * Some javadoc.
   * 
   * Logs an exception being thrown response.
   * 
   * @param methodName The name of the method.
   * @return A response indicating an exception.
   */
  public ResponseEntity<ObjectNode> threwAnException(String methodName) {
    Logger.error("Answer " + methodName + " : thew an exception.");
    return response.threwAnException();
  }

  /**
   * Some javadoc.
   * 
   * Logs a successful content deletion response.
   * 
   * @param methodName The name of the method.
   * @return A response indicating successful deletion.
   */
  public ResponseEntity<String> deletedSuccessfully(String methodName) {
    Logger.info("Answer " + methodName + " : deleted successfully.");
    return response.deletedSuccessfully();
  }

  /**
   * Some javadoc.
   * 
   * Logs a empty answer request.
   * 
   * @param methodName The name of the method.
   * @return A response indicating an empty answer.
   */
  public ResponseEntity<ObjectNode> emptyAnswer(String methodeName) {
    Logger.info("Answer " + methodeName + " : empty answer.");
    return response.emptyAnswer();
  }

  /**
   * Some javadoc.
   * 
   * Logs a successfully generated answer.
   * 
   * @param methodName The name of the method.
   * @return A response indicating successful generation.
   */
  public ResponseEntity<ObjectNode> successfullyGenerated(String methodeName, ObjectNode mainObject) {
    Logger.info("Answer " + methodeName + " : successfully generated.");
    return response.successfullyGenerated(mainObject);
  }

  /**
   * Some javadoc.
   * 
   * Logs a existing mapping between an address and a firestation.
   * 
   * @param methodName The name of the method.
   * @return A response indicating ExistingMapping.
   */
  public ResponseEntity<String> ExistingMappingBetweenAddressAndFirestation(String methodeName) {
    Logger.error("Answer " + methodeName + " : existing mapping between the address and the fire station.");
    return response.ExistingMapping();
  }

  /**
   * Some javadoc.
   * 
   * Logs a existing Person error.
   * 
   * @param methodName The name of the method.
   * @return A response indicating a existing mapping.
   */
  public ResponseEntity<String> ExistingPerson(String methodeName) {
    Logger.error("Answer " + methodeName + " : existing person with this first and last name.");
    return response.ExistingMapping();
  }

  /**
   * Some javadoc.
   * 
   * Logs a existing medical record error.
   * 
   * @param methodName The name of the method.
   * @return A response indicating a existing mapping.
   */
  public ResponseEntity<String> ExistingMedicalRecord(String methodeName) {
    Logger.error("Answer " + methodeName + " : existing medicalRecord with this first and last.");
    return response.ExistingMapping();
  }
}