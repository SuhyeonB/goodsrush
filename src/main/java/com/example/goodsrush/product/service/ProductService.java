package com.example.goodsrush.product.service;

import com.example.goodsrush.order.service.OrderService;
import com.example.goodsrush.product.dto.request.CreateProductRequest;
import com.example.goodsrush.product.dto.request.UpdateProductRequest;
import com.example.goodsrush.product.dto.response.ProductResponse;
import com.example.goodsrush.product.entity.Product;
import com.example.goodsrush.product.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final OrderService orderService;

    @Transactional
    public ProductResponse createProduct(CreateProductRequest dto) {
        Product product = Product.builder()
                .name(dto.getName())
                .stock(dto.getStock())
                .price(dto.getPrice())
                .build();

        productRepository.save(product);

        return ProductResponse.from(product);
    }

    @Transactional(readOnly = true)
    public Page<ProductResponse> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(ProductResponse::from);
    }

    @Transactional(readOnly = true)
    public ProductResponse getProduct(Long id) {
        return productRepository.findById(id)
                .map(ProductResponse::from)
                .orElseThrow(() -> new EntityNotFoundException("Product not found: " + id));
    }

    @Transactional
    public ProductResponse updateProduct(Long id, UpdateProductRequest dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found: " + id));

        product.update(dto.getName(), dto.getStock(), dto.getPrice());

        return ProductResponse.from(product);
    }

    @Transactional
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new EntityNotFoundException("Product not found: " + id);
        }

        productRepository.deleteById(id);
    }
}
