package com.sprintboot.product.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "products")
public class Product {
    @Transient
    public static final String SEQUENCE_NAME = "products_sequence";
    @Id
    private Integer id;
    @NotNull(message = "Product name should not be null")
    private String name;
    @Valid
    @NotNull(message = "Product category should not be null.....")
    private Category category;
    private String currency;
    @Min(0)
    private BigDecimal price;
    @Max(100)
    @Min(0)
    private BigDecimal discount;
    private String discountDescription;
    private List<String> imageURLs;
}
