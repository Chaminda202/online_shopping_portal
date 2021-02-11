package com.sprintboot.product.controller;

import com.sprintboot.product.model.Product;
import com.sprintboot.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping(value = "v1")
@RequiredArgsConstructor
@Validated
public class ProductController {
    private final ProductService productService;

    @PostMapping(value = "product")
    ResponseEntity<Product> addProduct(@Valid @RequestBody Product productDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.productService.addProduct(productDTO));
    }

    @GetMapping(value = "productList")
    ResponseEntity<List<Product>> productList() {
        return ResponseEntity.ok()
                .body(this.productService.productList());
    }

    @GetMapping(value = "productList/{category}")
    ResponseEntity<List<Product>> productCategoryList(@PathVariable @NotNull String category) {
        return ResponseEntity.ok()
                .body(this.productService.productCategoryList(category));
    }

    @GetMapping(value = "product/{id}")
    ResponseEntity<Product> productCategoryList(@PathVariable @Min(0) Integer id) {
        return ResponseEntity.ok()
                .body(this.productService.productById(id));
    }

    @PutMapping(value = "product/{id}")
    ResponseEntity<Product> updateProduct(@Valid @RequestBody Product productDTO, @PathVariable @NotNull Integer id) {
        productDTO.setId(id);
        return ResponseEntity.ok()
                .body(this.productService.updateProduct(productDTO));
    }

    @DeleteMapping(value = "product/{id}")
    ResponseEntity<Void> deleteProduct(@PathVariable @NotNull Integer id) {
        this.productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
