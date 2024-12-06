package net.shahid.microservices.myservice.service;

import lombok.RequiredArgsConstructor;
import net.shahid.microservices.myservice.domain.entity.Localization;
import net.shahid.microservices.myservice.domain.entity.Product;
import net.shahid.microservices.myservice.domain.enums.Type;
import net.shahid.microservices.myservice.exception.ProductNotFoundException;
import net.shahid.microservices.myservice.repository.ProductRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    @Cacheable("products")
    public Page<Product> getAllProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findByActiveTrue(pageable);
    }

    @Cacheable("custom")
    public Product getProductById(String id) {
        Product product = productRepository.findByIdAndActiveTrue(id).orElse(null);
        if (product == null) {
            throw new ProductNotFoundException("id = " + id);
        }
        return product;
    }

    @Cacheable("products")
    public Page<Product> getAllProductsByType(String type, int page, int size) {
        Type requiredType = Type.getByName(type);
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findAllByTypeAndActiveTrue(requiredType, pageable);
    }

    public void createProduct(Product product) {
        productRepository.save(product);
    }

    public Product updateProduct(String id, Product product) {
        return productRepository.findByIdAndActiveTrue(id).map(existingProduct -> {
            Optional.ofNullable(product.getTitle()).ifPresent(newTitle -> {
                Localization existingTitle = existingProduct.getTitle();
                Optional.ofNullable(newTitle.getAr()).ifPresent(existingTitle::setAr);
                Optional.ofNullable(newTitle.getEn()).ifPresent(existingTitle::setEn);
                Optional.ofNullable(newTitle.getFr()).ifPresent(existingTitle::setFr);
            });
            Optional.ofNullable(product.getType()).ifPresent(existingProduct::setType);
            existingProduct.setUpdatedAt(LocalDateTime.now());
            return productRepository.save(existingProduct);
        }).orElseThrow(() -> new ProductNotFoundException("id = " + id));
    }

    public void deleteProduct(String id) {
        Product product = productRepository.findByIdAndActiveTrue(id).orElse(null);
        if(product == null) {
            throw new ProductNotFoundException("id = " + id);
        }
        product.setActive(false);
        productRepository.save(product);
    }
}
