package com.example.riwaq.Advice;


import com.example.riwaq.Api.ApiException;
import com.example.riwaq.Api.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;


@RestControllerAdvice
public class ControllerAdvice {


        @ExceptionHandler(value = ApiException.class)
        public ResponseEntity<ApiResponse> handleApiException(ApiException e) {
            String msg = e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(msg));
        }

        @ExceptionHandler(value = MethodArgumentNotValidException.class)
        public ResponseEntity<ApiResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
            String msg = e.getFieldError().getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(msg));
        }

        @ExceptionHandler(value = ConstraintViolationException.class)
        public ResponseEntity<ApiResponse> handleConstraintViolationException(ConstraintViolationException e) {
            String msg = e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(msg));
        }

        @ExceptionHandler(value = DataIntegrityViolationException.class)
        public ResponseEntity<ApiResponse> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
            String msg = e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(msg));
        }

        @ExceptionHandler(value = InvalidDataAccessResourceUsageException.class)
        public ResponseEntity<ApiResponse> handleInvalidDataAccessResourceUsageException(InvalidDataAccessResourceUsageException e) {
            String msg = e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(msg));
        }

        @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
        public ResponseEntity<ApiResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
            String msg = e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(msg));
        }

        @ExceptionHandler(value = HttpMessageNotReadableException.class)
        public ResponseEntity<ApiResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
            String msg = e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(msg));
        }

        @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
        public ResponseEntity<ApiResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
            String msg = e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(msg));
        }
        @ExceptionHandler(value = NoResourceFoundException.class)
    public ResponseEntity<ApiResponse> handleNoResourceFoundException(NoResourceFoundException e) {
            String msg = e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(msg));
        }
    }

