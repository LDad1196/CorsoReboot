package com.example.demo.converter;

import com.example.demo.data.DTO.CorsoDTO;
import com.example.demo.data.DTO.DocenteDTO;
import com.example.demo.data.entity.Corso;
import com.example.demo.service.DocenteService;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public class CorsoMapper {


    @Autowired
    DocenteService docenteService;

    public CorsoDTO toDto(Corso corso) {
        CorsoDTO dto = new CorsoDTO();
        dto.setNome(corso.getNome());
        dto.setAnno_accademico(corso.getAnno_accademico());
        dto.setId_docente(corso.getId_docente());
        if (corso.getId_docente() != null) {
            DocenteDTO docente = docenteService.getDocenteById(corso.getId_docente());
            dto.setDocente(docente);
        }
        return dto;
    }

    public Corso toEntity(CorsoDTO dto) {
        Corso corso = new Corso();
        corso.setNome(dto.getNome());
        corso.setAnno_accademico(dto.getAnno_accademico());
        corso.setId_docente(dto.getId_docente());
        return corso;
    }
}
