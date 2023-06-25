package com.example.reviews.repository;

import com.example.reviews.model.Product;
import com.example.reviews.model.Review;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Example Review repository class this might be replaced by any other kind of database implementation to
 * satisfy the Review Controller needs
 */
@Repository("ReviewRepository")
public class ReviewRepositoryImpl implements ReviewRepository{


  /**
   * Review Repository Constructor
   */
  public ReviewRepositoryImpl() {
    this.productList.addAll(PRODUCT_BOOTSTRAP);
    this.reviewList.addAll(REVIEW_BOOTSTRAP);
  }

  /**
   * Users bootstrap to initialize the Product repository
   */
  private final List<Product> PRODUCT_BOOTSTRAP = List.of(
    new Product("2", List.of("1020", "1021")),
    new Product("3", List.of("1030")),
    new Product("4", List.of("1040", "1041", "1042")),
    new Product("5", List.of("1050", "1051"))
  );

  /**
   * Review bootstrap to initialize the Review repository
   */
  private final List<Review> REVIEW_BOOTSTRAP = List.of(
    new Review("1020", "Very cramped :( Do not recommend.", 2),
    new Review("1030", 3),
    new Review("1040", 5),
    new Review("1050", "Amazing! Would Fly Again!", 5),
    new Review("1051", 5),
    new Review("1041", "Reusable!", 5),
    new Review("1021", "Got me to the Moon!", 4),
    new Review("1042", 5)
  );

  public List<Product> productList = new ArrayList();

  public List<Review> reviewList = new ArrayList();

  /**
   * Method to find the list of reviews for a specific Product
   * @param product Given product to find reviews
   * @return Found list of Reviews
   */
  public List<Review> findReviewsByProduct(Product product) {
    List<Review> productReviews = new ArrayList<>();
    System.out.println(product.id());

    Product foundProduct = productList.stream()
      .filter(product1 -> product1.id().equals(product.id()))
      .findFirst()
      .orElse(null);

    if (foundProduct != null) {
      productReviews = reviewList.stream()
        .filter(review ->
          foundProduct.reviews().contains(review.id())
        ).toList();
    }
    return productReviews;
  }

  /**
   * Method to return all Reviews in the repository
   *
   * @return Full list of Review
   */
  public List<Review> findAll() {
    return reviewList;
  }

  /**
   * Method to find Review by its ID
   * @param id Given Review ID
   * @return Found Review
   */
  public Review findReviewById(String id) {
    return reviewList.stream()
      .filter(review -> review.id().equals(id))
      .findFirst()
      .orElse(null);
  }


  /**
   * Method to add a new Review
   * @param productId Product ID
   * @param id New Review ID
   * @param text New Review Text
   * @param starRating New Review Star Rating
   * @return Newly created Review
   */
  public Review addReview(String productId, String id, String text, int starRating) {
    Review newReview = new Review(id, text, starRating);
    reviewList.add(newReview);

    Product productExists = productList.stream()
      .filter(product -> product.id().equals(productId))
      .findFirst().orElse(null);

    if (productExists == null) {
      productList.add(new Product(productId, List.of(newReview.id())));
    } else {
      productExists.reviews().add(newReview.id());
    }

    return newReview;
  }


}
