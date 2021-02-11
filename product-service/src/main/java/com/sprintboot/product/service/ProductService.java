package com.sprintboot.product.service;

import com.sprintboot.product.model.Product;

import java.util.List;

public interface ProductService {
    Product addProduct(Product product);
    List<Product> productList();
    List<Product> productCategoryList(String category);
    Product productById(Integer id);
    Product updateProduct(Product product);
    void deleteProduct(Integer id);
}
