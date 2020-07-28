package com.liviubiur.productservice.product.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.liviubiur.productservice.product.persistence.entity.Product;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
