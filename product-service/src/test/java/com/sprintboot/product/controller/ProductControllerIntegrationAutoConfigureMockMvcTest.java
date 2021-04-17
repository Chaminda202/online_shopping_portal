package com.sprintboot.product.controller;

import com.sprintboot.product.ProductServiceApplication;
import com.sprintboot.product.model.Product;
import com.sprintboot.product.util.EntityUtil;
import com.sprintboot.product.util.JacksonUtil;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(classes = ProductServiceApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
class ProductControllerIntegrationAutoConfigureMockMvcTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    void addProduct() throws Exception {
        Product product = EntityUtil.createProduct();
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/v1/product").contentType(MediaType.APPLICATION_JSON)
                        .content(JacksonUtil.convertObjectToJson(product)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.currency").value(CoreMatchers.equalTo(product.getCurrency())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(CoreMatchers.equalTo(product.getPrice().doubleValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.discount").value(CoreMatchers.equalTo(product.getDiscount().doubleValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.discountDescription").value(CoreMatchers.equalTo(product.getDiscountDescription())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.category.id").value(CoreMatchers.equalTo(product.getCategory().getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.category.name").value(CoreMatchers.equalTo(product.getCategory().getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.category.brand").value(CoreMatchers.equalTo(product.getCategory().getBrand())));

        ResultActions resultActions = this.mockMvc
                .perform(MockMvcRequestBuilders.post("/v1/product").contentType(MediaType.APPLICATION_JSON)
                        .content(JacksonUtil.convertObjectToJson(product)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
        Product response = JacksonUtil.convertJsonToObject(resultActions.andReturn().getResponse().getContentAsString(), Product.class);
        assertEquals(product.getName(), response.getName());
        assertEquals(product.getPrice(), response.getPrice());
    }

    @Test
    @Order(2)
    void findProductByCategoryName() throws Exception {
        Product expectedResult = EntityUtil.createProduct();
        ResultActions resultActions = this.mockMvc
                .perform(MockMvcRequestBuilders.get("/v1/productList/{category}", expectedResult.getCategory().getName()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].id").value(Matchers.hasItem(expectedResult.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].currency").value(Matchers.hasItem(expectedResult.getCurrency())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].price").value(Matchers.hasItem(expectedResult.getPrice().doubleValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].discount").value(Matchers.hasItem(expectedResult.getDiscount().doubleValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].discountDescription").value(Matchers.hasItem(expectedResult.getDiscountDescription())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].category.id").value(Matchers.hasItem(expectedResult.getCategory().getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].category.name").value(Matchers.hasItem(expectedResult.getCategory().getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].category.brand").value(Matchers.hasItem(expectedResult.getCategory().getBrand())));
    }

    @Test
    @Order(3)
    void findProductById() throws Exception {
        Product expectedResult = EntityUtil.createProduct();
        ResultActions resultActions = this.mockMvc
                .perform(MockMvcRequestBuilders.get("/v1/product/{id}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(CoreMatchers.equalTo(expectedResult.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.currency").value(CoreMatchers.equalTo(expectedResult.getCurrency())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(CoreMatchers.equalTo(expectedResult.getPrice().doubleValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.discount").value(CoreMatchers.equalTo(expectedResult.getDiscount().doubleValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.discountDescription").value(CoreMatchers.equalTo(expectedResult.getDiscountDescription())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.category.id").value(CoreMatchers.equalTo(expectedResult.getCategory().getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.category.name").value(CoreMatchers.equalTo(expectedResult.getCategory().getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.category.brand").value(CoreMatchers.equalTo(expectedResult.getCategory().getBrand())));
    }

}