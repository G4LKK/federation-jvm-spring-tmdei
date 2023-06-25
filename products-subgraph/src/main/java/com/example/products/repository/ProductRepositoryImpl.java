package com.example.products.repository;

import com.example.products.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Example Product repository class this might be replaced by any other kind of database implementation to
 * satisfy the Product Controller needs
 */
@Repository("ProductRepository")
public class ProductRepositoryImpl implements ProductRepository{

  /**
   * Review Repository Constructor
   */
  public ProductRepositoryImpl() {
    this.productList.addAll(PRODUCT_BOOTSTRAP);
  }

  /**
   * Users bootstrap to initialize the Product repository
   */
  private final List<Product> PRODUCT_BOOTSTRAP = List.of(
    new Product("1","Saturn V", "The Original Super Heavy-Lift Rocket!"),
    new Product("2","Lunar Module"),
    new Product("3","Space Shuttle"),
    new Product("4","Falcon 9", "Reusable Medium-Lift Rocket"),
    new Product("5","Dragon", "Reusable Medium-Lift Rocket"),
    new Product("6","Starship", "Super Heavy-Lift Reusable Launch Vehicle")
  );

  public List<Product> productList = new ArrayList();

  /**
   * Method to return all Products in the repository
   *
   * @return Full list of Products
   */
  public List<Product> findAll() {
    return productList;
  }

  /**
   * Method to find Product by its ID
   * @param id Given Product ID
   * @return Found Product
   */
  public Product findProductById(String id) {
    return productList.stream()
      .filter(product -> product.id().equals(id))
      .findFirst()
      .orElse(null);
  }

  /**
   * Method to find Product by its id
   * @param id Given id to search Product
   * @return Found Product
   */
  public List<Product> findProductByIdBatch(List<String> id) {
    return productList.stream()
      .filter(product -> id.contains(product.id()))
      .toList();
  }

  /**
   * Method to add a new Product
   * @param id New Product ID
   * @param name New Product Name
   * @param description New Product Description
   * @return Newly created product
   */
  public Product addProduct(String id, String name, String description) {
    Product newProduct = new Product(id,name,description);

    productList.add(newProduct);

    return newProduct;
  }

}
