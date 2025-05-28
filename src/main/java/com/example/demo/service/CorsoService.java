package com.example.demo.service;

import com.example.demo.converter.CorsoMapper;
import com.example.demo.data.DTO.CorsoDTO;
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
    private CorsoMapper corsoMapper;

    public List<CorsoDTO> findAll() {
        return corsoRepository.findAll()
                .stream()
                .map(corsoMapper::toDto)
                .toList();
    }

    public CorsoDTO findById(Integer id_corso) {
        return corsoRepository.findById(id_corso)
                .map(corsoMapper::toDto)
                .orElse(null);
    }

    public CorsoDTO save(CorsoDTO corsoDTO) {
        Corso corso = corsoMapper.toEntity(corsoDTO);
        corso = corsoRepository.save(corso);
        return corsoMapper.toDto(corso);
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
        corso = corsoRepository.save(corso);
        return corsoMapper.toDto(corso);
    }


    public void delete(Integer id_corso) {
        corsoRepository.deleteById(id_corso);
    }
}
