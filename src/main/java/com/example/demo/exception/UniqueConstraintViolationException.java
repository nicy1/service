package com.example.demo.exception;

import lombok.Getter;

@Getter
public class UniqueConstraintViolationException extends RuntimeException {

    private final String message;
    private final String fieldName;

    public UniqueConstraintViolationException(String className, String fieldName) {
        this.fieldName = fieldName;
        this.message = String.format("Resource %s with id %s already exists.", className, fieldName);
    }
}
