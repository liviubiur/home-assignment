package com.liviubiur.productservice.product.rest.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.liviubiur.productservice.product.persistence.entity.Product;
import com.liviubiur.productservice.product.rest.ProductRestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProductResourceAssembler implements RepresentationModelAssembler<Product,
    EntityModel<Product>> {

  @Override
  public EntityModel<Product> toModel(Product product) {
    return new EntityModel<>(product,
        linkTo(methodOn(ProductRestController.class).getById(product.getId())).withSelfRel(),
        linkTo(methodOn(ProductRestController.class).getAll()).withRel("products"));
  }
}
