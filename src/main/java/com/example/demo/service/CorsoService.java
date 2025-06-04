package com.example.demo.service;

import com.example.demo.converter.CorsoMapper;
import com.example.demo.data.DTO.CorsoDTO;
import com.example.demo.data.DTO.DocenteDTO;
import com.example.demo.data.DTO.DiscenteDTO;
import com.example.demo.data.entity.Corso;
import com.example.demo.repository.CorsoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CorsoService {

    @Autowired
    CorsoRepository corsoRepository;

    @Autowired
    CorsoMapper corsoMapper;

    @Autowired
    DocenteService docenteService;

    @Autowired
    Corsi_discentiService corsi_discentiService;

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
            if(!docenteService.docenteExists(corsoDTO.getId_docente())) {
                throw new IllegalArgumentException("Docente non trovato");
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
            // Validazione docente
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

        if(corso.getId_docente() != null) {
            try {
                DocenteDTO docente = docenteService.getDocenteById(corso.getId_docente());
                dto.setDocente(docente);
            } catch (Exception e) {
                System.err.println("Errore: " + e.getMessage());
            }
        }
        if (corso.getId_corso() != null) {
            try {
                List<DiscenteDTO> discenti = corsi_discentiService.getDiscentiByCourseId(corso.getId_corso());
                dto.setDiscenti(discenti);
                dto.setNumero_discenti(discenti.size());
            } catch (Exception e) {
                System.err.println("Errore recupero discenti: " + e.getMessage());
                dto.setDiscenti(List.of());
                dto.setNumero_discenti(0);
            }
        }
        return dto;
    }

    public CorsoDTO iscriviDiscente(Integer id_corso, Integer id_discente) {
        // Verifica che il corso esista
        if (!corsoRepository.existsById(id_corso)) {
            throw new IllegalArgumentException("Corso non trovato con ID: " + id_corso);
        }
        corsi_discentiService.iscriviDiscente(id_corso, id_discente);
        return findById(id_corso);
    }


}
