package com.burakkarahan.productservice.repository;

import com.burakkarahan.productservice.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {

}