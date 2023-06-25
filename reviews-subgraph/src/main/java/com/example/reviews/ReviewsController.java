package com.example.reviews;

import com.example.reviews.model.Review;
import com.example.reviews.model.Product;
import com.example.reviews.repository.ReviewRepository;
import com.example.reviews.repository.ReviewRepositoryImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Review Controller
 */
@AllArgsConstructor
@Controller
public class ReviewsController {

  /**
   * Review Repository to be used
   */
  @Autowired
  private final ReviewRepository repo;

  @SchemaMapping
  public CompletableFuture<List<Review>> reviews(Product show) {
    return CompletableFuture.supplyAsync(() -> {
      return repo.findReviewsByProduct(show);
    });
  }

  /**
   * GraphQL Query to find all available Reviews
   *
   * @return All Reviews
   */
  @QueryMapping
  public CompletableFuture<List<Review>> reviews() {
    return CompletableFuture.supplyAsync(() -> {
      return repo.findAll();
    });
  }

  /**
   * GraphQL Query to find a specific Review by its ID
   *
   * @param id Id to look for
   * @return Found Review
   */
  @QueryMapping
  public CompletableFuture<Review> review(@Argument String id) {
    return CompletableFuture.supplyAsync(() -> {
      return repo.findReviewById(id);
    });
  }

  /**
   * GraphQL Mutation to add Review and Reivew to Product association
   *
   * @param productId  Product Id
   * @param id         New Review ID
   * @param starRating New Review Star Rating
   * @param text       New Review Text
   * @return Newly created Review
   */
  @MutationMapping
  public CompletableFuture<Review> addReview(@Argument String productId, @Argument String id, @Argument int starRating, @Argument String text) {
    return CompletableFuture.supplyAsync(() -> {
      return repo.addReview(productId, id, text, starRating);
    });
  }
}
