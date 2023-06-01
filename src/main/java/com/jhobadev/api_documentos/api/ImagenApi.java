package com.jhobadev.api_documentos.api;

import com.jhobadev.api_documentos.servicio.DocumentoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;

@RestController
public class ImagenApi {

    DocumentoServicio documentoServicio;

    @Autowired
    public ImagenApi(DocumentoServicio documentoServicio) {
        this.documentoServicio = documentoServicio;
    }

    @GetMapping("/imagenes/{imagen}")
    public ResponseEntity<?> mostrarImagen(@PathVariable("imagen") String imagen) {
        try {
            Resource resource = documentoServicio.mostrarImagen(imagen);

            boolean esJpgOPng = imagen.toLowerCase().endsWith(".jpg") || imagen.toLowerCase().endsWith(".jpeg");
            String contentType = esJpgOPng ? MediaType.IMAGE_JPEG_VALUE
                    : MediaType.IMAGE_PNG_VALUE;

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);
        } catch (MalformedURLException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ruta equivocada"); // Devuelve una respuesta 400 Bad Request en caso de URL inválida
        }
    }

}
