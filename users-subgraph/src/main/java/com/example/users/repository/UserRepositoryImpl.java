package com.example.users.repository;

import com.example.users.model.Review;
import com.example.users.model.User;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Example User repository class this might be replaced by any other kind of database implementation to
 * satisfy the User Controller needs
 */
@Repository("UserRepository")
public class UserRepositoryImpl implements UserRepository{

  /**
   * User Repository constructor
   */
  public UserRepositoryImpl() {
    this.userList.addAll(USERS_BOOTSTRAP);
    this.reviewList.addAll(REVIEW_BOOTSTRAP);
  }

  /**
   * Users bootstrap to initialize the user repository
   */
  private final List<User> USERS_BOOTSTRAP = List.of(
    new User("1020", 60, "John Shine"),
    new User("1021", 30, "Jack"),
    new User("1030", 55, "Joaquim Almeida"),
    new User("1040", 40, "Mark Barnes"),
    new User("1041", 27, "Marcus"),
    new User("1042", 35, "Yasmina"),
    new User("1050", 28, "Josh Downes"),
    new User("1050", 35, "Mohamed")
  );

  /**
   * Review bootstrap to initialize the review repository
   */
  private final List<Review> REVIEW_BOOTSTRAP = List.of(
    new Review("1020", "1020"),
    new Review("1021", "1021"),
    new Review("1030", "1030"),
    new Review("1040", "1040"),
    new Review("1041", "1041"),
    new Review("1042", "1042"),
    new Review("1050", "1050"),
    new Review("1051", "1050")
  );

  public List<User> userList = new ArrayList();

  public List<Review> reviewList = new ArrayList();

  /**
   * Method to find user by the provided Review
   * @param review Given Review to search for User
   * @return Found User
   */
  public User findUserByReview(Review review) {
    return userList.stream()
      .filter(user -> user.id().equals(
        reviewList.stream()
          .filter(r -> r.id().equals(review.id()))
          .findFirst()
          .orElse(null).userId()
      ))
      .findFirst()
      .orElse(null);
  }

  @BatchMapping(typeName = "Review")
  public Mono<Map<User,Review>> findUserByReviewBatch(Review review) {
    Mono<Map<User,Review>> result = Mono.empty();
    User foundUser = null;
    Review foundReview = reviewList.stream()
      .filter(r -> r.id().equals(review.id()))
      .findFirst()
      .orElse(null);

    if(foundReview != null) {
      foundUser = userList.stream()
        .filter(user -> user.id().equals(foundReview.userId()
        ))
        .findFirst()
        .orElse(null);

      result = Mono.just(Map.of(foundUser,review));
    }

    return result;
  }

  /**
   * Method to return all Users in the repository
   * @return Full list of Users
   */
  public List<User> findAll() {
    return userList;
  }

  /**
   * Method to find User by its id
   * @param id Given id to search User
   * @return Found User
   */
  public User findUserById(String id) {
    return userList.stream()
      .filter(user -> user.id().equals(id))
      .findFirst()
      .orElse(null);
  }

  /**
   * Method to find User by its id
   * @param id Given id to search User
   * @return Found User
   */
  public List<User> findUserByIdBatch(List<String> id) {
    return userList.stream()
      .filter(user -> id.contains(user.id()))
      .toList();
  }

  /**
   * Method to add a new User and Review to User association
   * @param reviewId Review Id to associate User
   * @param id New User Id
   * @param name New User Name
   * @param age New User Age
   * @return Newly created User
   */
  public User addUser(String reviewId, String id, String name, int age) {
    User newUser = new User(id, age, name);
    userList.add(newUser);
    reviewList.add(new Review(reviewId,id));
    return newUser;
  }


}
