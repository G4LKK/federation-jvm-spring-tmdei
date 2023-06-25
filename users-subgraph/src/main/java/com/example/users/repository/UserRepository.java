package com.example.users.repository;

import com.example.users.model.Review;
import com.example.users.model.User;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

public interface UserRepository {

  public User findUserByReview(Review review);

  public Mono<Map<User,Review>> findUserByReviewBatch(Review review);

  public List<User> findAll();

  public User findUserById(String id);

  public List<User> findUserByIdBatch(List<String> id);

  public User addUser(String reviewId, String id, String name, int age);

}
