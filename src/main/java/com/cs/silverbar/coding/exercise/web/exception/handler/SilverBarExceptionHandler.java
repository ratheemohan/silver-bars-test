package com.cs.silverbar.coding.exercise.web.exception.handler;

import com.cs.silverbar.coding.exercise.exception.OrderNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpHeaders.EMPTY;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
@Slf4j
public class SilverBarExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = OrderNotFoundException.class)
    public ResponseEntity NotFoundError(Exception ex, WebRequest webRequest) {
        logger.error("Not found error:{}", ex);
        return handleExceptionInternal(ex, "Not found", EMPTY, NOT_FOUND, webRequest);
    }

    @Override
    protected ResponseEntity handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return super.handleExceptionInternal(ex, body, headers, status, request);
    }
}
