package com.openclassrooms.tourguide.communUtils;

/**
 * Some javadoc.
 * 
 * This class can contains different utility methods for modified or checking and get
 * strings.
 */
public class CommunUtilsTools {
  /**
   * Some javadoc.
   * Retrieves the name of the current method being executed.
   *
   * @return The name of the current method.
   */
  public static String getCurrentMethodName() {
    StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[2];
    return stackTraceElement.getMethodName();
  }
}
