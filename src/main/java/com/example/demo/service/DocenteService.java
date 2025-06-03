package com.example.demo.service;

import com.example.demo.converter.DocenteClient;
import com.example.demo.data.DTO.DocenteDTO;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocenteService {
    @Autowired
    private DocenteClient docenteClient;

    public DocenteDTO getDocenteById(Integer id_docente) {
        if (id_docente == null) {
            return null;
        }

        try {

            DocenteDTO result = docenteClient.getDocenteById(id_docente);
            return result;
        } catch (FeignException.NotFound e) {
            System.err.println("Docente non trovato per ID: " + id_docente);
            return null;
        } catch (FeignException e) {
            System.err.println("Errore chiamata API docenti: " + e.getMessage());
            return null;
        }
    }

    public boolean docenteExists(Integer id_docente) {
        DocenteDTO docente = getDocenteById(id_docente);
        return docente != null;
    }

}
