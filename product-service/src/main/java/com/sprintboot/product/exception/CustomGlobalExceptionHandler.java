package com.sprintboot.product.exception;

import com.sprintboot.product.model.APIError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        //Get all field errors
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getField() + " : "+ x.getDefaultMessage())
                .collect(Collectors.toList());

        APIError errorResponse = APIError.builder()
                .timeStamp(LocalDateTime.now())
                .pathUrl(request.getContextPath())
                .errors(errors)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ProductException.class})
    public ResponseEntity<Object> handleProductException(Exception ex, ServletWebRequest request) {
        APIError errorResponse = APIError.builder()
                .timeStamp(LocalDateTime.now())
                .pathUrl(request.getDescription(true))
                .errors(Arrays.asList(ex.getMessage()))
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {RecordNotExistException.class})
    public ResponseEntity<Object> handleRecordNotExistException(Exception ex, ServletWebRequest request) {
        APIError errorResponse = APIError.builder()
                .timeStamp(LocalDateTime.now())
                .pathUrl(request.getDescription(true))
                .errors(Arrays.asList(ex.getMessage()))
                .statusCode(HttpStatus.NOT_FOUND.value())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // should be updated
    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(
            MissingPathVariableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(ex, null, headers, status, request);
    }

    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> methodArgumentTypeMismatchException(Exception ex, ServletWebRequest request) {
        APIError errorResponse = APIError.builder()
                .timeStamp(LocalDateTime.now())
                .pathUrl(request.getDescription(true))
                .errors(Arrays.asList(ex.getMessage()))
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public final ResponseEntity<Object> handleConstraintViolationException(Exception ex, ServletWebRequest request) {
        APIError errorResponse = APIError.builder()
                .timeStamp(LocalDateTime.now())
                .pathUrl(request.getDescription(true))
                .errors(Arrays.asList(ex.getMessage().split(":")[1].trim()))
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .build();
        return ResponseEntity.badRequest().body(errorResponse);
    }
}