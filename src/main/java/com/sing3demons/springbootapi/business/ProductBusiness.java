package com.sing3demons.springbootapi.business;

import java.util.Objects;

import org.springframework.stereotype.Service;

import com.sing3demons.springbootapi.exception.BaseException;
import com.sing3demons.springbootapi.exception.ProductException;

@Service
public class ProductBusiness {

    public String getProductById(String id) throws ProductException {
        if (Objects.equals("12", id)) {
            throw ProductException.notFound();
        }
        return "id: " + id;

    }

}
