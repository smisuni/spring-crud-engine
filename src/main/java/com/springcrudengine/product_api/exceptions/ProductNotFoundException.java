package com.springcrudengine.product_api.exceptions;


    public class ProductNotFoundException extends RuntimeException {
        public ProductNotFoundException(String message) {
            super(message);
        }
    }
