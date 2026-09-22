package com.example.goodsrush.order.service;

import com.example.goodsrush.order.dto.request.CreateOrderRequest;
import com.example.goodsrush.order.repository.OrderRepository;
import com.example.goodsrush.product.entity.Product;
import com.example.goodsrush.product.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
class OrderServiceTest {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderService orderService;

    @AfterEach
    void tearDown() {
        orderRepository.deleteAll();
        productRepository.deleteAll();
    }

    @Test
    void 재고차감_락없음_동시성문제_재현() throws InterruptedException {
        // given
        Product product = productRepository.save(Product.builder()
                .name("limited goods")
                .stock(10)
                .price(10000)
                .build());

        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        CountDownLatch latch = new CountDownLatch(threadCount);

        // when
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    orderService.purchaseWithoutLock(product.getId(), new CreateOrderRequest("user A", 1));
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();

        // then
        Product result = productRepository.findById(product.getId()).orElseThrow();
        System.out.println("최종 재고: " + result.getStock());

        long productOrderCount = orderRepository.countByProductId(product.getId());
        System.out.println("성공한 주문 수: " + productOrderCount);
    }

    @Test
    void 재고차감_비관락_정합성유지() throws InterruptedException {
        // given
        Product product = productRepository.save(Product.builder()
                .name("한정판 굿즈")
                .stock(10)
                .price(10000)
                .build());

        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        // when
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    orderService.purchase(product.getId(), new CreateOrderRequest("user", 1));
                } catch (Exception e) {
                    // 재고 부족은 정상적으로 발생해야 하는 예외
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();

        // then
        Product result = productRepository.findById(product.getId()).orElseThrow();
        long orderCount = orderRepository.countByProductId(product.getId());
        System.out.println("최종 재고: " + result.getStock());
        System.out.println("성공한 주문 수: " + orderCount);
        // 기대: 재고 0, 주문 수 정확히 10
    }
}