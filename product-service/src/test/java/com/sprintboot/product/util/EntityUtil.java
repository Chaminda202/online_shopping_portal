package com.sprintboot.product.util;

import com.sprintboot.product.model.Category;
import com.sprintboot.product.model.Product;

import java.math.BigDecimal;

public class EntityUtil {
    private EntityUtil() {
    }

    public static Product createProduct() {
        Product product = Product.builder()
                .id(1)
                .currency("USD")
                .price(new BigDecimal(10.5))
                .discount(new BigDecimal(1.5))
                .category(Category.builder()
                        .id(1)
                        .name("Test")
                        .brand("Test")
                        .build())
                .name("Bubble Tea")
                .build();
        return product;
    }
}
