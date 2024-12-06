package net.shahid.microservices.myservice.controller;

import lombok.RequiredArgsConstructor;
import net.shahid.mbc.api.gen.ProductApi;
import net.shahid.mbc.api.gen.model.ProductDto;
import net.shahid.mbc.api.gen.model.ProductRequest;
import net.shahid.microservices.myservice.domain.entity.Product;
import net.shahid.microservices.myservice.domain.mapper.ProductMapper;
import net.shahid.microservices.myservice.exception.ProductNotFoundException;
import net.shahid.microservices.myservice.service.ProductService;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ProductController implements ProductApi {
    private final ProductService productService;
    private final ProductMapper productMapper;

    @Override
    public ResponseEntity<List<ProductDto>> getAllProducts(Integer page, Integer size) {
        List<ProductDto> collect = productService.getAllProducts(page, size)
                .stream()
                .map(productMapper::mapToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(collect);
    }

    @Override
    public ResponseEntity<ProductDto> getProductById(String id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(productMapper.mapToDto(product));
    }

    @Override
    public ResponseEntity<List<ProductDto>> getProductsByType(String type,
                                                              Integer page,
                                                              Integer size) {
        Page<Product> products = productService.getAllProductsByType(type, page, size);
        if (products.isEmpty()) {
            throw new ProductNotFoundException("No available products found of this type");
        }
        return new ResponseEntity<>(products.stream()
                .map(productMapper::mapToDto)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteProduct(String id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ProductDto> updateProduct(String id,
                                                    ProductRequest productRequest) {
        Product product = productMapper.mapFromRequest(productRequest);
        Product newProduct = productService.updateProduct(id, product);
        return new ResponseEntity<>(productMapper.mapToDto(newProduct), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> createProduct(ProductRequest productRequest) throws BadRequestException {
        productRequestValidation(productRequest);
        Product product = productMapper.mapFromRequest(productRequest);
        productService.createProduct(product);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    private void productRequestValidation(ProductRequest productRequest) throws BadRequestException {
        if (productRequest.getTitleAr() == null || productRequest.getTitleAr().isEmpty()) {
            throw new BadRequestException("missing arabic title");
        }
        if (productRequest.getType() == null || productRequest.getType().isEmpty()) {
            throw new BadRequestException("missing type");
        }
    }
}
