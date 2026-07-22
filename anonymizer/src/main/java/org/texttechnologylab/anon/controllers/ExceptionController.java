package org.texttechnologylab.anon.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.texttechnologylab.anon.exceptions.BadInputException;
import org.texttechnologylab.anon.exceptions.MissingAnonTypeException;
import org.texttechnologylab.anon.exceptions.MissingModalityException;
import org.texttechnologylab.anon.exceptions.MissingUriException;

import java.util.Map;
@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(MissingUriException.class)
    public ResponseEntity<?> handleMissingUri(MissingUriException exception){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message", exception.getMessage()));
    }
    @ExceptionHandler
    public ResponseEntity<?> handleBadInput(BadInputException exception){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message", exception.getMessage()));
    }
    @ExceptionHandler(MissingModalityException.class)
    public ResponseEntity<?> handleMissingModality(MissingModalityException exception){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message", exception.getMessage()));
    }
    @ExceptionHandler(MissingAnonTypeException.class)
    public ResponseEntity<?> handleNoAnonType(MissingAnonTypeException exception){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message", exception.getMessage()));
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneric(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("message", "Something went wrong on the server."));
    }
}
