package com.liviubiur.productservice.product.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.liviubiur.productservice.product.persistence.entity.Product;
import com.liviubiur.productservice.product.service.ProductService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/products", produces = APPLICATION_JSON_VALUE)
public class ProductRestController implements ProductRestApi {

  private static final String ID = "/{id}";
  
  private final ProductService productService;

  @Autowired
  public ProductRestController(
      ProductService productService) {
    this.productService = productService;
  }

  @Override
  @GetMapping
  public CollectionModel<EntityModel<Product>> getAll() {
    return new CollectionModel<>(productService.getAll(),
        linkTo(methodOn(ProductRestController.class).getAll()).withSelfRel());
  }

  @Override
  @GetMapping(ID)
  public EntityModel<Product> getById(@PathVariable Long id) {
    return productService.getById(id);
  }

  @Override
  @PostMapping
  public ResponseEntity<?> newProduct(@RequestBody Product product) {
    EntityModel<Product> newProduct = productService.newProduct(product);

    return ResponseEntity.created(newProduct.getRequiredLink(IanaLinkRelations.SELF).toUri())
        .body(newProduct);
  }

  @Override
  @PutMapping(ID)
  public ResponseEntity<?> updateProduct(@RequestBody Product product, @PathVariable Long id) {
    EntityModel<Product> updatedProduct = productService.updateProduct(product, id);

    return ResponseEntity.created(updatedProduct.getRequiredLink(IanaLinkRelations.SELF).toUri())
        .body(updatedProduct);
  }

  @Override
  @DeleteMapping(ID)
  public ResponseEntity<?> deleteById(@PathVariable Long id) {
    productService.deleteById(id);

    return ResponseEntity.noContent().build();
  }
}
