package com.sprintboot.product.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "product")
@Data
public class ProductProperties {
    private List<String> currencies;
}
