package com.sprintboot.product.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Data
@Builder
@ToString
public class Category {
    private Integer id;
    @NotNull(message = "Category name should not be null")
    private String name;
    private String brand;
}
