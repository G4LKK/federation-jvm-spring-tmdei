package com.example.reviews.repository;

import com.example.reviews.model.Product;
import com.example.reviews.model.Review;

import java.util.List;

public interface ReviewRepository {

  public List<Review> findReviewsByProduct(Product product);

  public List<Review> findAll();

  public Review findReviewById(String id);

  public Review addReview(String productId, String id, String text, int starRating);

}
