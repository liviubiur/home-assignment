package com.liviubiur.homeassignment.product.rest.exception;

public class ProductNotFoundException extends RuntimeException {

  public ProductNotFoundException(Long id) {
    super(String.format("Could not find product with [id=%s]", id));
  }

}
