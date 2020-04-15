package com.liviubiur.homeassignment.product.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.liviubiur.homeassignment.product.persistence.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
