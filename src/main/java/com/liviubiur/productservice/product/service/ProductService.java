package com.liviubiur.productservice.product.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.liviubiur.productservice.product.persistence.repository.ProductRepository;
import com.liviubiur.productservice.product.rest.assembler.ProductResourceAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import com.liviubiur.productservice.product.persistence.entity.Product;
import com.liviubiur.productservice.product.rest.exception.ProductNotFoundException;

@Service
public class ProductService {

  private final ProductRepository productRepository;
  private final ProductResourceAssembler assembler;

  @Autowired
  public ProductService(
      ProductRepository productRepository, ProductResourceAssembler assembler) {
    this.productRepository = productRepository;
    this.assembler = assembler;
  }

  public List<EntityModel<Product>> getAll() {
    return productRepository.findAll().stream()
        .map(assembler::toModel)
        .collect(Collectors.toList());
  }

  public EntityModel<Product> getById(Long id) {
    Product product = productRepository.findById(id)
        .orElseThrow(() -> new ProductNotFoundException(id));

    return assembler.toModel(product);
  }

  public EntityModel<Product> newProduct(Product product) {
    product.setCreatedAt(LocalDateTime.now());
    return assembler.toModel(productRepository.save(product));
  }

  public EntityModel<Product> updateProduct(Product product, Long id) {
    Product updatedProduct = productRepository.findById(id)
        .map(newProduct -> {
          newProduct.setName(product.getName());
          newProduct.setPrice(product.getPrice());
          newProduct.setCreatedAt(LocalDateTime.now());
          return productRepository.save(newProduct);
        })
        .orElseGet(() -> {
          product.setId(id);
          return productRepository.save(product);
        });

    return assembler.toModel(updatedProduct);
  }

  public void deleteById(Long id) {
    productRepository.deleteById(id);
  }
}
