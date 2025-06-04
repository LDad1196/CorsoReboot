package com.example.demo.service;

import com.example.demo.configuration.WebClientConfig;
import com.example.demo.data.DTO.DocenteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DocenteService {

    @Autowired
    WebClientConfig webClient;

    public DocenteDTO getDocenteById(Integer id_docente) {
        return webClient.webClient().get()
                .uri("/docenti/{id_docente}", id_docente)
                .retrieve()
                .bodyToMono(DocenteDTO.class)
                .block();
    }

    public boolean docenteExists(Integer id_docente) {
        DocenteDTO docente = getDocenteById(id_docente);
        return docente != null;
    }

}
