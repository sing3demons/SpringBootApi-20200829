package com.sing3demons.springbootapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @GetMapping("/product")
    public String getProduct() {
        return "product";
    }

    @GetMapping("/products")
    public String listProduct() {
        return "all products";
    }

}
