package com.example.demo.service;

import com.example.demo.data.DTO.DiscenteDTO;
import com.example.demo.data.entity.Corsi_discenti;
import com.example.demo.repository.Corsi_discentiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class Corsi_discentiService {

    @Autowired
    Corsi_discentiRepository corsi_discentiRepository;

    @Autowired
    DiscenteService discenteService;

    public List<DiscenteDTO> getDiscentiByCourseId(Integer id_corso) {
       if (id_corso == null) {
           return List.of();
       }
       List<Corsi_discenti> iscrizioni = corsi_discentiRepository.findById_corso(id_corso);

       List<Integer> idsDiscenti = iscrizioni.stream()
               .map(Corsi_discenti::getId_discente)
               .collect(Collectors.toList());

       return discenteService.getDiscentiByIds(idsDiscenti);
    }

    public Corsi_discenti iscriviDiscente(Integer id_corso, Integer id_discente) {
        if (!discenteService.discenteExists(id_discente)) {
            throw new IllegalArgumentException("Discente non trovato con ID: " + id_discente);
        }
        if (corsi_discentiRepository.existsById_corsoAndId_discente(id_corso, id_discente)) {
            throw new IllegalArgumentException("Discente già iscritto a questo corso");
        }
        Corsi_discenti iscrizione = new Corsi_discenti(id_corso, id_discente);
        return corsi_discentiRepository.save(iscrizione);
    }


    public Integer contaDiscenti(Integer id_corso) {
        return corsi_discentiRepository.countDiscentiById_corso(id_corso);
    }

}
