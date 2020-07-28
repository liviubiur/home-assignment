package com.liviubiur.productservice.product.persistence.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Data
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String name;
  private double price;

  @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
  @DateTimeFormat(pattern = "dd/MM/yyyy")
  private LocalDateTime createdAt;

}
