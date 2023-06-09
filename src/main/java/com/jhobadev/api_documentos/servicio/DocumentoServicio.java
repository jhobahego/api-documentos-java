package com.jhobadev.api_documentos.servicio;

import com.jhobadev.api_documentos.entidades.Documento;
import com.jhobadev.api_documentos.repositorio.DocumentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DocumentoServicio {

    private final Path carpetaImagenes = Paths.get("imagenes");
    DocumentoRepository documentoRepository;

    @Autowired
    public DocumentoServicio(DocumentoRepository documentoRepository){
        this.documentoRepository = documentoRepository;
    }

    public String guardarImagen(MultipartFile file) throws IOException {
        Path rutaImagen = this.carpetaImagenes.resolve(Objects.requireNonNull(file.getOriginalFilename()));

        if(!Files.exists(rutaImagen)){
            Files.copy(file.getInputStream(), rutaImagen);
        }

        String rutaRelativa = rutaImagen.toString().replace("\\", "/");

        String rutaCompleta = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/" + UriUtils.encodePath(rutaRelativa, "UTF-8")).toUriString();

        return rutaCompleta;
    }

    public Resource mostrarImagen(String nombreImagen) throws MalformedURLException {
        Path rutaImagen = this.carpetaImagenes.resolve(nombreImagen);

        return new UrlResource(rutaImagen.toUri());
    }

    public Documento guardarDocumento(Documento documento, MultipartFile file) throws IOException {

        // Guarda el archivo y retorna la ruta donde se guarda
        String urlImagen = guardarImagen(file);
        documento.setImagen(urlImagen);

        return documentoRepository.save(documento);
    }

    public List<Documento> obtenerDocumentos() {
        return documentoRepository.findAll();
    }

    public Optional<Documento> obtenerDocumentoPorId(Long documentoId) {
        return documentoRepository.findById(documentoId);
    }

    public Documento actualizarStock(Long id, int stock) {
        Optional<Documento> documento = documentoRepository.findById(id);

        if(documento.isEmpty()){
            throw new IllegalArgumentException("No se encontro el documento a actualizar");
        }

        Documento doc = documento.get();
        doc.setStock(stock);

        return documentoRepository.save(doc);
    }

    public void eliminarDocumento(Long id) {
        Optional<Documento> documento = obtenerDocumentoPorId(id);

        if(documento.isEmpty()){
            throw new IllegalArgumentException("documento con id: " + id + "no encontrado");
        }

        documentoRepository.deleteById(id);
    }
}
