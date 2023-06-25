package com.example.reviews.model;

import java.util.List;

public record Product(String id, List<String> reviews) {
  public static final String PRODUCT_TYPE = "Product";
}
