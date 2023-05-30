package com.jhobadev.api_documentos.repositorio;

import com.jhobadev.api_documentos.entidades.Documento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentoRepository extends JpaRepository<Documento, Long> {

}
