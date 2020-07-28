package com.liviubiur.productservice.product.rest;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

import com.liviubiur.productservice.product.persistence.entity.Product;

public interface ProductRestApi {

  /**
   * Get all existing products
   *
   * @return the collection model with all existing products
   */
  CollectionModel<EntityModel<Product>> getAll();

  /**
   * Get an existing product entity by id
   *
   * @param id the id of the requested product
   * @return the entity model with the desired product entity
   */
  EntityModel<?> getById(Long id);

  /**
   * Create a new product
   *
   * @param product the product entity to create
   * @return the response entity for the new product entity
   */
  ResponseEntity<?> newProduct(Product product);

  /**
   * Update/Replace an existing product entity by id
   *
   * @param id the id of the product entity that should be updated/replaced
   * @param product the updated product entity
   * @return the response entity with the updated/replaced product entity
   */
  ResponseEntity<?> updateProduct(Product product, Long id);

  /**
   * Delete an existing product entity by id
   *
   * @param id the id of the product resource that should be deleted
   * @return the response entity with a delete message
   */
  ResponseEntity<?> deleteById(Long id);
}
