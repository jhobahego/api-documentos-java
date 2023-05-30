package com.jhobadev.api_documentos.entidades;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Documento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "documento_id", nullable = false)
    private Long documento_id;

    @Column(name = "tipo_documento", nullable = false)
    TipoDocumento tipoDocumento;

    @Column(name = "autor", nullable = false)
    String autor;

    @Column(name = "titulo", nullable = false)
    String titulo;

    @Column(name = "descripcion", nullable = false)
    String descripcion;

    @Column(name = "imagen", nullable = false)
    String imagen;

    @Column(name = "categoria", nullable = false)
    Categoria categoria;

    @Column(name = "stock", nullable = false)
    int stock;

    @Column(name = "precio", nullable = false)
    int precio;

    @Column(name = "editorial", nullable = false)
    String editorial;

    @Column(name = "idioma", nullable = false)
    String idioma;

    @Column(name = "paginas", nullable = false)
    int paginas;
}
