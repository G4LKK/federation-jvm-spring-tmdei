package com.example.users.model;

/**
 * User Model
 * @param id User ID
 * @param name User Name
 * @param age User Age
 */
public record User(String id, String name, int age) {

  /**
   * User constructor
   * @param id New User ID
   * @param age New User Age
   * @param name New User Name
   */
  public User(String id, int age, String name) {
    this(id,name,age);
  }
}
