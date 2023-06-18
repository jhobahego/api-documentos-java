package com.jhobadev.api_documentos.api;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ServerWebInputException;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;

@ControllerAdvice
public class ImageExceptionHandler {

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<String> imagenNoEncontrada(FileNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se ha encontrado la imagen con ese nombre");
    }

    @ExceptionHandler({
            MalformedURLException.class,
            HttpClientErrorException.BadRequest.class,
            DuplicateKeyException.class,
            WebExchangeBindException.class,
            HttpMessageNotReadableException.class,
            ServerWebInputException.class
    })
    public ResponseEntity<String> badRequestImage() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Error al cargar imagen, intentelo nuevamente mas tarde");
    }
}
