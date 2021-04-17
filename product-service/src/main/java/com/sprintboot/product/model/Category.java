package com.sprintboot.product.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    private Integer id;
    @NotNull(message = "Category name should not be null")
    private String name;
    private String brand;
}
