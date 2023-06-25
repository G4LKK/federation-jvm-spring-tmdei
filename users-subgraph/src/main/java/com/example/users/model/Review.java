package com.example.users.model;

/**
 * Review Model
 * @param id Review ID
 * @param userId User ID assoacited with Review
 */
public record Review(String id,String userId) {
  /**
   * For GraphQL Configuration purposes (Schema type solving)
   */
  public static final String REVIEW_TYPE = "Review";
}
