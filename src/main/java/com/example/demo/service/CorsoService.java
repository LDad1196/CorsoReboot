package com.example.demo.service;

import com.example.demo.converter.CorsoMapper;
import com.example.demo.data.DTO.CorsoDTO;
import com.example.demo.data.DTO.DocenteDTO;
import com.example.demo.data.entity.Corso;
import com.example.demo.repository.CorsoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CorsoService {

    @Autowired
    CorsoRepository corsoRepository;

    @Autowired
    CorsoMapper corsoMapper;

    @Autowired
    DocenteService docenteService;


    public List<CorsoDTO> findAll() {
        return corsoRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    public CorsoDTO findById(Integer id_corso) {
        return corsoRepository.findById(id_corso)
                .map(this::convertToDto)
                .orElse(null);
    }

    public CorsoDTO save(CorsoDTO corsoDTO) {
        if (corsoDTO.getId_docente() != null) {
            if (!docenteService.docenteExists(corsoDTO.getId_docente())) {
                throw new IllegalArgumentException("Docente non trovato con ID: " + corsoDTO.getId_docente());
            }
        }
        Corso corso = corsoMapper.toEntity(corsoDTO);
        corso = corsoRepository.save(corso);
        return convertToDto(corso);
    }


    public CorsoDTO update(Integer id_corso, CorsoDTO corsoDTO) {
        Corso corso = corsoRepository.findById(id_corso)
                .orElseThrow(() -> new IllegalArgumentException("Corso non trovato"));
        if (corsoDTO.getNome() != null) {
            corso.setNome(corsoDTO.getNome());
        }
        if (corsoDTO.getAnno_accademico() != null) {
            corso.setAnno_accademico(corsoDTO.getAnno_accademico());
        }
        if (corsoDTO.getId_docente() != null) {
            if (!docenteService.docenteExists(corsoDTO.getId_docente())) {
                throw new IllegalArgumentException("Docente non trovato con ID: " + corsoDTO.getId_docente());
            }
            corso.setId_docente(corsoDTO.getId_docente());
        }
        corso = corsoRepository.save(corso);
        return convertToDto(corso);
    }


    public void delete(Integer id_corso) {
        corsoRepository.deleteById(id_corso);
    }

    private CorsoDTO convertToDto(Corso corso) {
        CorsoDTO dto = corsoMapper.toDto(corso);
        if (corso.getId_docente() != null) {
            try {
                DocenteDTO docente = docenteService.getDocenteById(corso.getId_docente());
                dto.setDocente(docente);
            } catch (Exception e) {
                System.err.println("Errore durante la conversione del corso in dto: " + e.getMessage());
            }
        }
        return dto;
    }
}
