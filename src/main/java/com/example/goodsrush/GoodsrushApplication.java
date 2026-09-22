package com.example.goodsrush;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@EnableRetry
@SpringBootApplication
public class GoodsrushApplication {

	public static void main(String[] args) {
		SpringApplication.run(GoodsrushApplication.class, args);
	}

}
