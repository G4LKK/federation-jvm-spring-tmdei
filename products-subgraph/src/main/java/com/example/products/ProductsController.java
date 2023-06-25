package com.example.products;

import com.example.products.model.Product;
import com.example.products.repository.ProductRepository;
import com.example.products.repository.ProductRepositoryImpl;
import io.micrometer.core.annotation.Timed;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Product Controller
 */
@AllArgsConstructor
@Controller
public class ProductsController {

  /**
   * Product Repository to be used
   */
  @Autowired
  private final ProductRepository repo;


  /**
   * GraphQL Query to find a specific Product by its ID
   *
   * @param id Id to look for
   * @return Found Product
   */
  @QueryMapping
  public CompletableFuture<Product> product(@Argument String id) {
    return CompletableFuture.supplyAsync(() -> {
      return repo.findProductById(id);
    });
  }

  @BatchMapping(typeName = "String")
  public Mono<Map<String, Product>> productBatch(@Argument List<String> id) {
    return
      Mono.just(repo.findProductByIdBatch(id).stream()
        .collect(Collectors.toMap(Product::id, Function.identity())));
  }

  /**
   * GraphQL Query to find all available Products
   *
   * @return All Products
   */
  @QueryMapping
  public CompletableFuture<List<Product>> products() {
    return CompletableFuture.supplyAsync(() -> {
      return repo.findAll();
    });
  }

  /**
   * GraphQL Mutation to add Product
   * @param id New Product ID
   * @param name New Product Name
   * @param description New Product Description
   * @return Newly created Product
   */
  @MutationMapping
  public CompletableFuture<Product> addProduct(@Argument String id, @Argument String name, @Argument String description){
    return CompletableFuture.supplyAsync(() -> {
      return repo.addProduct(id, name, description);
    });
  }
}
