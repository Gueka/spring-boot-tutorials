package net.gueka.user.controller;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import net.gueka.user.exception.BadFormatException;
import net.gueka.user.exception.NotFoundException;
import net.gueka.user.model.ExceptionResponse;

@ControllerAdvice
@RestController
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(NotFoundException.class)
  public final ResponseEntity<ExceptionResponse> handleNotFoundException(NotFoundException ex, WebRequest request) {

    ExceptionResponse exceptionResponse = ExceptionResponse.builder()
        .timestamp(new Date())
        .mensaje(ex.getMessage())
        .code("001")
        .build();

    return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.NOT_FOUND);

  }

  @ExceptionHandler(BadFormatException.class)
  public final ResponseEntity<ExceptionResponse> handleBadFormatException(BadFormatException ex, WebRequest request) {

    ExceptionResponse exceptionResponse = ExceptionResponse.builder()
        .timestamp(new Date())
        .mensaje(ex.getMessage())
        .code("002")
        .build();

    return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.BAD_REQUEST);

  }

}