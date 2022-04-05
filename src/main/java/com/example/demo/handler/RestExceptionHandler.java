package com.example.demo.handler;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.UniqueConstraintViolationException;
import com.example.demo.util.ErrorCode;
import com.example.demo.util.ApiError;
import org.hibernate.HibernateException;
import org.hibernate.exception.SQLGrammarException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityExistsException;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    /// Handler for validation
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        List<ApiError> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.add(ApiError.of(ErrorCode.getRelativeCode(fieldName), fieldName, errorMessage));
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ApiError error = ApiError.of(ErrorCode.RESOURCE_NOT_FOUND.getCode(), null, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(value = UniqueConstraintViolationException.class)
    public ResponseEntity<ApiError> handleUniqueConstraintViolationException(UniqueConstraintViolationException ex) {
        String fieldName = ex.getFieldName();
        ApiError error = ApiError.of(ErrorCode.getRelativeCode(fieldName), fieldName, ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(value = Exception.class)
    protected ResponseEntity<ApiError> handleInternalServerError(RuntimeException ex) {
        ApiError error = ApiError.of(ErrorCode.SERVER_ERROR.getCode(), null, "Internal server error");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
