package com.jhobadev.api_documentos.servicio;

import com.jhobadev.api_documentos.entidades.Documento;
import com.jhobadev.api_documentos.repositorio.DocumentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
        Files.copy(file.getInputStream(), rutaImagen);

        return rutaImagen.toString();
    }

    public Resource mostrarImagen(String nombreImagen) throws MalformedURLException {
        Path rutaImagen = this.carpetaImagenes.resolve(nombreImagen);

        return new UrlResource(rutaImagen.toUri());
    }

    public List<Documento> obtenerDocumentos() {
        return documentoRepository.findAll();
    }

    public Optional<Documento> obtenerDocumentoPorId(Long documentoId) {
        return documentoRepository.findById(documentoId);
    }

    public Documento actualizarDocumento(Long id, Documento documentoCambiado) {
        Optional<Documento> documento = documentoRepository.findById(id);

        if(!documento.isPresent()){
            throw new IllegalArgumentException("No se encontro el documento a actualizar");
        }

        Documento doc = documento.get();
        doc.setTipoDocumento(documentoCambiado.getTipoDocumento());
        doc.setAutor(documentoCambiado.getAutor());
        doc.setTitulo(documentoCambiado.getTitulo());
        doc.setDescripcion(documentoCambiado.getDescripcion());
        doc.setCategoria(documentoCambiado.getCategoria());
        doc.setStock(documentoCambiado.getStock());
        doc.setPrecio(documentoCambiado.getPrecio());
        doc.setImagen(documentoCambiado.getImagen());
        doc.setEditorial(documentoCambiado.getEditorial());
        doc.setIdioma(documentoCambiado.getIdioma());
        doc.setPaginas(documentoCambiado.getPaginas());

        return documentoRepository.save(doc);
    }

    public void eliminarDocumento(Long id) {
        documentoRepository.deleteById(id);
    }
}
