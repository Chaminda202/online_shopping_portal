package com.sprintboot.product.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
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
@Document(collection = "products")
@ApiModel("Contains all attributes of the product")
public class Product {
    @Transient
    public static final String SEQUENCE_NAME = "products_sequence";

    @ApiModelProperty("Unique id of the product")
    @Id
    private Integer id;

    @ApiModelProperty("Unique id of the product")
    @NotNull(message = "Product name should not be null")
    private String name;

    @Valid
    @NotNull(message = "Product category should not be null.....")
    private Category category;

    @ApiModelProperty("Currency type")
    private String currency;
    @Min(0)

    @ApiModelProperty("Price of the product")
    private BigDecimal price;

    @ApiModelProperty("Discount of the product")
    @Max(100)
    @Min(0)
    private BigDecimal discount;

    @ApiModelProperty("Discount description of the product")
    private String discountDescription;

    @ApiModelProperty("Images url of the product")
    private List<String> imageURLs;
}
