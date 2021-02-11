package com.sprintboot.product.repository;

import com.sprintboot.product.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, Integer> {
    @Query("{'category.name':?0}")
    List<Product>  findByCategory(String category);
}
