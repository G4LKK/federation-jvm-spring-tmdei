package com.example.users;

import com.example.users.model.Review;
import com.example.users.model.User;
import com.example.users.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.*;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Users Controller
 */
@AllArgsConstructor
@Controller
public class UsersController {

  /**
   * User Repository to be used
   */
  @Autowired
  private final UserRepository repo;

  /**
   * Method to handle the schema mapping of User from Review
   * This is required as Review is extended to have a associated User
   *
   * @param review Review to search User for
   * @return Found User for Review
   */
  @SchemaMapping
  public CompletableFuture<User> user(Review review) {
    return CompletableFuture.supplyAsync(() -> {
      User result = null;
      if (repo.findUserByReviewBatch(review).block() != null) {
        result = repo.findUserByReviewBatch(review).block().keySet().iterator().next();
      }
      return result;
    });
  }

  /**
   * GraphQL Query to find all available Users
   *
   * @return All Users
   */
  @QueryMapping
  public CompletableFuture<List<User>> users() {
    return CompletableFuture.supplyAsync(() -> {
      return repo.findAll();
    });
  }

  /**
   * GraphQL Query to find a specific User by its ID
   *
   * @param id Id to look for
   * @return Found User
   */
  @QueryMapping
  public CompletableFuture<User> user(@Argument String id) {
    return CompletableFuture.supplyAsync(() -> {
      return repo.findUserById(id);
    });
  }

//  /**
//   * GraphQL Query to find a specific User by its ID
//   *
//   * @param id Id to look for
//   * @return Found User
//   */
//  @BatchMapping(typeName = "String")
//  public Mono<Map<String, User>> userBatch(@Argument List<String> id) {
//    return
//      Mono.just(repo.findUserByIdBatch(id).stream()
//        .collect(Collectors.toMap(User::id,Function.identity())));
//  }

  /**
   * GraphQL Mutation to add User and User to Review association
   *
   * @param reviewId Review ID to associate User
   * @param id       New User ID
   * @param age      New User Age
   * @param name     New User Name
   * @return
   */
  @MutationMapping
  public CompletableFuture<User> addUser(@Argument String reviewId, @Argument String id, @Argument int age, @Argument String name) {
    return CompletableFuture.supplyAsync(() -> {
      return repo.addUser(reviewId, id, name, age);
    });
  }
}
