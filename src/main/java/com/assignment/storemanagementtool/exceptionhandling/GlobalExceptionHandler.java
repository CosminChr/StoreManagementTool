package com.assignment.storemanagementtool.exceptionhandling;

import com.assignment.storemanagementtool.exception.OrderNotFoundException;
import com.assignment.storemanagementtool.exception.OutOfStockException;
import com.assignment.storemanagementtool.exception.ProductNotFoundException;
import com.assignment.storemanagementtool.exception.UserNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.WebUtils;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler({ OrderNotFoundException.class, OutOfStockException.class, ProductNotFoundException.class})
  public final ResponseEntity<ApiError> handleException(Exception ex, WebRequest request) {
    var headers = new HttpHeaders();

    if (ex instanceof OrderNotFoundException orderNotFound) {
      var status = HttpStatus.NOT_FOUND;

      return handleOrderNotFoundException(orderNotFound, headers, status, request);
    } else if (ex instanceof OutOfStockException outOfStockException) {
      var status = HttpStatus.NOT_FOUND;

      return handleOutOfStockException(outOfStockException, headers, status, request);
    } else if (ex instanceof UserNotFoundException userNotFoundException) {
      var status = HttpStatus.BAD_REQUEST;

      return handleUserNotFoundException(userNotFoundException, headers, status, request);
    } else if (ex instanceof ProductNotFoundException productNotFoundException) {
      var status = HttpStatus.NOT_FOUND;

      return handleProductNotFoundException(productNotFoundException, headers, status, request);
    } else {
      var status = HttpStatus.INTERNAL_SERVER_ERROR;
      return handleExceptionInternal(ex, null, headers, status, request);
    }
  }

  protected ResponseEntity<ApiError> handleOrderNotFoundException(OrderNotFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    return handleExceptionInternal(ex, new ApiError(ex.getMessage()), headers, status, request);
  }

  protected ResponseEntity<ApiError> handleOutOfStockException(OutOfStockException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    return handleExceptionInternal(ex, new ApiError(ex.getMessage()), headers, status, request);
  }

  protected ResponseEntity<ApiError> handleProductNotFoundException(ProductNotFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    return handleExceptionInternal(ex, new ApiError(ex.getMessage()), headers, status, request);
  }

  protected ResponseEntity<ApiError> handleUserNotFoundException(UserNotFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    return handleExceptionInternal(ex, new ApiError(ex.getMessage()), headers, status, request);
  }

  protected ResponseEntity<ApiError> handleExceptionInternal(Exception ex, ApiError body, HttpHeaders headers, HttpStatus status, WebRequest request) {
    if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
      request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
    }

    return new ResponseEntity<>(body, headers, status);
  }
}
