package com.sing3demons.springbootapi.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sing3demons.springbootapi.business.ProductBusiness;
import com.sing3demons.springbootapi.exception.ProductException;

@RestController
@RequestMapping("/api")
public class ProductApi {
    private final ProductBusiness business;

    public ProductApi(ProductBusiness business) {
        this.business = business;
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<String> getProduct(@PathVariable("id") String id) throws ProductException  {
        String productById = business.getProductById(id);
        return ResponseEntity.ok(productById);

    }

}
