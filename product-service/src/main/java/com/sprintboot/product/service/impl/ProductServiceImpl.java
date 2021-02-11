package com.sprintboot.product.service.impl;

import com.sprintboot.product.config.ProductProperties;
import com.sprintboot.product.exception.ProductException;
import com.sprintboot.product.exception.RecordNotExistException;
import com.sprintboot.product.model.Product;
import com.sprintboot.product.repository.ProductRepository;
import com.sprintboot.product.service.ProductService;
import com.sprintboot.product.service.SequenceGeneratorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
   // static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);
    private final ProductRepository productRepository;
    private final SequenceGeneratorService sequenceGeneratorService;
    private final ProductProperties productProperties;

    @Override
    public Product addProduct(Product product) {
        log.info("Inside addProduct method in product service - {}", product.toString());
        // validate amount
        if (product.getPrice().equals(0) && product.getDiscount().compareTo(BigDecimal.ZERO) > 0) {
            throw new ProductException("No discount is allowed for 0 price product");
        }
        // validate currency
        if (!this.productProperties.getCurrencies().contains(product.getCurrency().toUpperCase())) {
            throw new ProductException("Invalid currency. Valid currencies " + this.productProperties.getCurrencies());
        }
        product.setId(this.sequenceGeneratorService.generateSequence(Product.SEQUENCE_NAME));
        return this.productRepository.save(product);
    }

    @Override
    public List<Product> productList() {
        log.info("Inside productList method in product service");
        return this.productRepository.findAll();
    }

    @Override
    public List<Product> productCategoryList(String category) {
        log.info("Inside productCategoryList method in product service - {}", category);
        return this.productRepository.findByCategory(category);
    }

    @Override
    public Product productById(Integer id) {
        log.info("Inside productById method in product service - {}", id);
        Optional<Product> optionalProduct = this.productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            return optionalProduct.get();
        }
        throw new RecordNotExistException("Product does not exist by id: " + id);
    }

    @Override
    public Product updateProduct(Product product) {
        log.info("Inside updateProduct method in product service - {}", product.toString());
        Optional<Product> optionalProduct = this.productRepository.findById(product.getId());
        if (optionalProduct.isPresent()) {
            return this.productRepository.save(product);
        }
        throw new RecordNotExistException("Product does not exist by id: " + product.getId());
    }

    @Override
    public void deleteProduct(Integer id) {
        log.info("Inside deleteProduct method in product service - {}", id);
        Optional<Product> optionalProduct = this.productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            this.productRepository.deleteById(id);
        }
        throw new RecordNotExistException("Product does not exist by id: " + id);
    }
}
