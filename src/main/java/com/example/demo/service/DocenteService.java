package com.example.demo.service;

import com.example.demo.data.DTO.DocenteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class DocenteService {
    @Autowired
    RestTemplate restTemplate;

    public DocenteDTO getDocenteById(Integer id_docente) {
        if (id_docente == null) {
            return null;
        }
        try {
            String url = "http://localhost:8080/docenti/" + id_docente;
            return restTemplate.getForObject(url, DocenteDTO.class);
        }  catch (RestClientException e) {
            System.err.println("Errore: " + e.getMessage());
            return null;
        }
    }

    public boolean docenteExists(Integer id_docente) {
        DocenteDTO docente = getDocenteById(id_docente);
        return docente != null;
    }
}
