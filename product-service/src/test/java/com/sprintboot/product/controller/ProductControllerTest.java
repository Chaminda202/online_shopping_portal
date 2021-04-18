package com.sprintboot.product.controller;

import com.sprintboot.product.model.Product;
import com.sprintboot.product.service.ProductService;
import com.sprintboot.product.util.EntityUtil;
import com.sprintboot.product.util.JacksonUtil;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

// @SpringBootTest(classes = ProductServiceApplication.class)
class ProductControllerTest {
    // @Mock
    ProductService productService;
    // @Autowired
    // private MappingJackson2HttpMessageConverter jacksonMessageConverter;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        this.productService = Mockito.mock(ProductService.class);
        MockitoAnnotations.initMocks(this);
        final ProductController controller = new ProductController(productService);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
    }
    @Test
    void addProduct() throws Exception {
        Product product = EntityUtil.createProduct();
        when(productService.addProduct(any())).thenReturn(product);
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
    void productList() throws Exception {
        Product expectedResult = EntityUtil.createProduct();
        when(productService.productList()).thenReturn(Arrays.asList(expectedResult));
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/v1/productList"))
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
    void productCategoryList() throws Exception{
        Product expectedResult = EntityUtil.createProduct();
        when(productService.productCategoryList(anyString())).thenReturn(Arrays.asList(expectedResult));
        this.mockMvc
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
    void findProductById() throws Exception {
        Product product = EntityUtil.createProduct();
        when(productService.productById(anyInt())).thenReturn(product);
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/v1/product/{id}", anyInt()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.currency").value(CoreMatchers.equalTo(product.getCurrency())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(CoreMatchers.equalTo(product.getPrice().doubleValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.discount").value(CoreMatchers.equalTo(product.getDiscount().doubleValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.discountDescription").value(CoreMatchers.equalTo(product.getDiscountDescription())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.category.id").value(CoreMatchers.equalTo(product.getCategory().getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.category.name").value(CoreMatchers.equalTo(product.getCategory().getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.category.brand").value(CoreMatchers.equalTo(product.getCategory().getBrand())));
    }

    @Test
    void updateProduct() throws Exception {
        Product product = EntityUtil.createProduct();
        when(productService.updateProduct(any())).thenReturn(product);
        this.mockMvc
                .perform(MockMvcRequestBuilders.put("/v1/product/{id}", anyInt()).contentType(MediaType.APPLICATION_JSON)
                        .content(JacksonUtil.convertObjectToJson(product)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.currency").value(CoreMatchers.equalTo(product.getCurrency())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(CoreMatchers.equalTo(product.getPrice().doubleValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.discount").value(CoreMatchers.equalTo(product.getDiscount().doubleValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.discountDescription").value(CoreMatchers.equalTo(product.getDiscountDescription())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.category.id").value(CoreMatchers.equalTo(product.getCategory().getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.category.name").value(CoreMatchers.equalTo(product.getCategory().getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.category.brand").value(CoreMatchers.equalTo(product.getCategory().getBrand())));
    }

    @Test
    void deleteProduct() throws Exception {
        doNothing().when(productService).deleteProduct(anyInt());
        this.mockMvc
                .perform(MockMvcRequestBuilders.delete("/v1/product/{id}", anyInt()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}