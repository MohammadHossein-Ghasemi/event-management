package co.muhu.eventManagement.controller;

import co.muhu.eventManagement.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.Map;

@ControllerAdvice
public class ExceptionHandling {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundEX(ResourceNotFoundException exception, HttpServletRequest request){
        Map<String,String> response =Map.of(
                "message",exception.getMessage(),
                "timestamp", LocalDateTime.now().toString(),
                "path",request.getServletPath(),
                "status",HttpStatus.NOT_FOUND.toString()
        );
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .header(HttpHeaders.LOCATION,request.getServletPath())
                .body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> defaultEx (Exception exception, HttpServletRequest request){
        Map<String,String> response =Map.of(
                "message",exception.getMessage(),
                "timestamp", LocalDateTime.now().toString(),
                "path",request.getServletPath(),
                "status",HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                "type",exception.getClass().toString()
        );
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .header(HttpHeaders.LOCATION,request.getServletPath())
                .body(response);
    }
}
