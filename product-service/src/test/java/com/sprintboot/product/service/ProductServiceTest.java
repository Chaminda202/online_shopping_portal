package com.sprintboot.product.service;

import com.sprintboot.product.config.ProductProperties;
import com.sprintboot.product.exception.ProductException;
import com.sprintboot.product.exception.RecordNotExistException;
import com.sprintboot.product.model.Product;
import com.sprintboot.product.repository.ProductRepository;
import com.sprintboot.product.service.impl.ProductServiceImpl;
import com.sprintboot.product.util.EntityUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/*@SpringBootTest(classes = ProductServiceApplication.class)*/
class ProductServiceTest {
    // @Mock
    ProductRepository productRepository;
    // @Mock
    SequenceGeneratorService sequenceGeneratorService;
    // @Mock
    ProductProperties productProperties;

    ProductService productService;

    @BeforeEach
    void setUp() {
        this.productRepository = Mockito.mock(ProductRepository.class);
        this.sequenceGeneratorService = Mockito.mock(SequenceGeneratorService.class);
        this.productProperties = Mockito.mock(ProductProperties.class);
        productService = new ProductServiceImpl(productRepository, sequenceGeneratorService, productProperties);
    }

    @Nested
    @DisplayName("All test cases for add product")
    class AddProduct {
        /**
         * Validate amount (price should be greater than discount price)
         */
        @Test
        @DisplayName("Price not validation scenario")
        void addProductValidateAmount() {
            Product product = EntityUtil.createProduct();
            product.setPrice(new BigDecimal(2));
            product.setDiscount(new BigDecimal(2.5));
            ProductException productException = assertThrows(ProductException.class,
                    () -> productService.addProduct(product));
            assertNotNull(productException);
            assertEquals("No discount is allowed for 0 price product", productException.getMessage());
        }

        /**
         * Validate currency
         */
        @Test
        @DisplayName("Currency not validation scenario")
        void addProductValidateCurrency() {
            Product product = EntityUtil.createProduct();
            when(productProperties.getCurrencies()).thenReturn(Arrays.asList("SGD", "AUD", "CAD"));
            ProductException productException = assertThrows(ProductException.class,
                    () -> productService.addProduct(product));
            assertNotNull(productException);
            assertEquals("Invalid currency. Valid currencies [SGD, AUD, CAD]", productException.getMessage());
        }

        /**
         * Add product successfully
         */
        @Test
        @DisplayName("Happy path - add product")
        void addProduct() {
            Product product = EntityUtil.createProduct();
            when(productProperties.getCurrencies()).thenReturn(Arrays.asList("USD", "AUD", "CAD"));
            when(sequenceGeneratorService.generateSequence(anyString())).thenReturn(5);
            when(productRepository.save(any())).thenReturn(product);

            Product actual = productService.addProduct(product);
            assertEquals(product.getName(), actual.getName());
            assertEquals(product.getPrice(), actual.getPrice());
            assertEquals(product.getDiscount(), actual.getDiscount());
            assertEquals(product.getCurrency(), actual.getCurrency());
            assertEquals(product.getDiscount(), actual.getDiscount());
        }
    }

    /**
     * Retrieve product list by category
     */
    @Test
    void productCategoryList() {
        Product product = EntityUtil.createProduct();
        when(this.productRepository.findByCategory(anyString())).thenReturn(Arrays.asList(product));
        List<Product> result = this.productService.productCategoryList(anyString());
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Nested
    @DisplayName("All test cases for find product by id")
    class FindProductById {
        @Test
        @DisplayName("Product id not exist scenario")
        void productByIdWhenIdNotExist() {
            when(productRepository.findById(anyInt())).thenReturn(Optional.empty());
            RecordNotExistException recordNotExistException = assertThrows(RecordNotExistException.class,
                    () -> productService.productById(5));
            assertNotNull(recordNotExistException);
            assertEquals("Product does not exist by id: 5", recordNotExistException.getMessage());
        }
        /**
         * Find product by id
         */
        @Test
        @DisplayName("Happy path - find product by id")
        void productById() {
            Product product = EntityUtil.createProduct();
            when(productRepository.findById(anyInt())).thenReturn(Optional.of(product));
            Product actual = productService.productById(anyInt());
            assertEquals(product.getName(), actual.getName());
            assertEquals(product.getPrice(), actual.getPrice());
            assertEquals(product.getDiscount(), actual.getDiscount());
            assertEquals(product.getCurrency(), actual.getCurrency());
            assertEquals(product.getDiscount(), actual.getDiscount());
        }
    }

    @Nested
    @DisplayName("All test cases for update product")
    class UpdateProduct {
        @Test
        @DisplayName("Product id not exist scenario")
        void productByIdWhenIdNotExist() {
            Product product = EntityUtil.createProduct();
            when(productRepository.findById(anyInt())).thenReturn(Optional.empty());
            RecordNotExistException recordNotExistException = assertThrows(RecordNotExistException.class,
                    () -> productService.updateProduct(product));
            assertNotNull(recordNotExistException);
            assertEquals("Product does not exist by id: "+ product.getId(), recordNotExistException.getMessage());
        }

        /**
         * Update product successfully
         */
        @Test
        @DisplayName("Happy path - update product")
        void updateProduct() {
            Product product = EntityUtil.createProduct();
            when(productRepository.findById(anyInt())).thenReturn(Optional.of(product));
            when(productRepository.save(any())).thenReturn(product);

            Product actual = productService.updateProduct(product);
            assertEquals(product.getName(), actual.getName());
            assertEquals(product.getPrice(), actual.getPrice());
            assertEquals(product.getDiscount(), actual.getDiscount());
            assertEquals(product.getCurrency(), actual.getCurrency());
            assertEquals(product.getDiscount(), actual.getDiscount());
        }
    }

    @Nested
    @DisplayName("All test cases for delete product")
    class DeleteProduct {
        @Test
        @DisplayName("Product id not exist scenario")
        void productByIdWhenIdNotExist() {
            when(productRepository.findById(anyInt())).thenReturn(Optional.empty());
            RecordNotExistException recordNotExistException = assertThrows(RecordNotExistException.class,
                    () -> productService.deleteProduct(5));
            assertNotNull(recordNotExistException);
            assertEquals("Product does not exist by id: 5", recordNotExistException.getMessage());
        }
        /**
         * Delete product successfully
         */
        @Test
        @DisplayName("Happy path - delete product")
        void deleteProduct() {
            Product product = EntityUtil.createProduct();
            when(productRepository.findById(anyInt())).thenReturn(Optional.of(product));
            doNothing().when(productRepository).deleteById(anyInt());
            assertDoesNotThrow(() -> productService.deleteProduct(anyInt()));
            verify(productRepository, times(1)).deleteById(anyInt());
        }
    }

    /**
     * Retrieve all product list
     */
    @Test
    void productList() {
        Product product = EntityUtil.createProduct();
        when(this.productRepository.findAll()).thenReturn(Arrays.asList(product));
        List<Product> result = this.productService.productList();
        // assertNotNull(result);
        // assertEquals(1, result.size());
        assertThat(result).isNotNull();
        assertThat(1).isEqualTo(result.size());
    }
}