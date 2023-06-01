package com.jhobadev.api_documentos.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.FileNotFoundException;

@ControllerAdvice
public class ImageExceptionHandler {

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<String> imagenNoEncontrada(FileNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se ha encontrado la imagen con ese nombre");
    }
}
