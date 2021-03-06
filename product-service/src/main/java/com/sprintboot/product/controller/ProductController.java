package com.sprintboot.product.controller;

import com.sprintboot.product.model.Product;
import com.sprintboot.product.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@Api(description = "Product API endpoints")
public class ProductController {
    private final ProductService productService;

    @PostMapping(value = "product")
    @ApiOperation("Adds product endpoint")
    ResponseEntity<Product> addProduct(@ApiParam("product details") @Valid @RequestBody Product productDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.productService.addProduct(productDTO));
    }

    @GetMapping(value = "productList")
    @ApiOperation("Retrieves products list endpoint")
    ResponseEntity<List<Product>> productList() {
        return ResponseEntity.ok()
                .body(this.productService.productList());
    }

    @GetMapping(value = "productList/{category}")
    @ApiOperation("Retrieves product by category endpoint")
    ResponseEntity<List<Product>> productCategoryList(@ApiParam("category of the products") @PathVariable @NotNull String category) {
        return ResponseEntity.ok()
                .body(this.productService.productCategoryList(category));
    }

    @GetMapping(value = "product/{id}")
    @ApiOperation("Retrieves product by id endpoint")
    ResponseEntity<Product> productCategoryList(@ApiParam("id of the product") @PathVariable @Min(0) Integer id) {
        return ResponseEntity.ok()
                .body(this.productService.productById(id));
    }

    @PutMapping(value = "product/{id}")
    @ApiOperation("Updates product endpoint")
    ResponseEntity<Product> updateProduct(@ApiParam("product details") @Valid @RequestBody Product productDTO,@ApiParam("id of the product") @PathVariable @NotNull Integer id) {
        productDTO.setId(id);
        return ResponseEntity.ok()
                .body(this.productService.updateProduct(productDTO));
    }

    @DeleteMapping(value = "product/{id}")
    @ApiOperation("Deletes product endpoint")
    ResponseEntity<Void> deleteProduct(@ApiParam("id of the product") @PathVariable @NotNull Integer id) {
        this.productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
