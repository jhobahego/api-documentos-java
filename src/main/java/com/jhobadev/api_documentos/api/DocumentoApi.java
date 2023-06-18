package com.jhobadev.api_documentos.api;

import com.jhobadev.api_documentos.entidades.Documento;
import com.jhobadev.api_documentos.servicio.DocumentoServicio;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class DocumentoApi {

    DocumentoServicio documentoServicio;

    @Autowired
    public DocumentoApi(DocumentoServicio documentoServicio) {
        this.documentoServicio = documentoServicio;
    }

    @GetMapping
    public ResponseEntity<List<Documento>> obtenerdocumentos() {
        List<Documento> documentos = documentoServicio.obtenerDocumentos();

        return ResponseEntity.ok(documentos);
    }

    @GetMapping("/documentos/{id}")
    public ResponseEntity<?> obtenerDocumento(@PathVariable("id") Long id) {
        Optional<Documento> documento = documentoServicio.obtenerDocumentoPorId(id);

        return documento.map(value -> ResponseEntity.ok().body(value))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(value = "/documentos", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> guardarDocumento(@ModelAttribute Documento documento, @RequestParam("file") MultipartFile file) {
        if(file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Debes seleccionar una imagen");
        }

        try {
            documentoServicio.guardarDocumento(documento, file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(documento);
    }

    @PutMapping(value = "/documentos/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> actualizarStock(@PathVariable("id") Long id, @RequestParam("stock") int stock) {
        try{
            Documento documentoActualizado = documentoServicio.actualizarStock(id, stock);
            return ResponseEntity.ok().body(documentoActualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/documentos/{id}")
    public ResponseEntity<?> eliminarDocumento(@PathVariable("id") Long id) {
        try{
            documentoServicio.eliminarDocumento(id);
        } catch(IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.noContent().build();
    }
}
