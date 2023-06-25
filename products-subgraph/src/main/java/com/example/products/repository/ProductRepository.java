package com.example.products.repository;

import com.example.products.model.Product;

import java.util.List;

public interface ProductRepository {

  public List<Product> findAll();

  public Product findProductById(String id);

  public Product addProduct(String id, String name, String description);

  public List<Product> findProductByIdBatch(List<String> id);

}
