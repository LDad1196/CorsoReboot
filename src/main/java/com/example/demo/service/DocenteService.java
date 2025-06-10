package com.example.demo.service;

import com.example.demo.configuration.WebClientConfig;
import com.example.demo.data.DTO.DocenteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;

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

    public DocenteDTO getOrCreateDocente(String nome, String cognome) {
        System.out.println("=== DEBUG getOrCreateDocente ===");
        System.out.println("Cercando docente: " + nome + " " + cognome);

        // Prima tenta di trovare il docente esistente
        DocenteDTO docenteEsistente = getDocenteByNomeAndCognome(nome, cognome);
        System.out.println("Docente esistente trovato: " + docenteEsistente);

        if (docenteEsistente != null) {
            System.out.println("Restituisco docente esistente con ID: " + docenteEsistente.getId_docente());
            return docenteEsistente;
        }

        // Se non esiste, lo crea
        System.out.println("Docente non trovato, creo nuovo docente...");
        DocenteDTO nuovoDocente = new DocenteDTO();
        nuovoDocente.setNome(nome);
        nuovoDocente.setCognome(cognome);

        try {
            DocenteDTO docenteCreato = webClient.webClient().post()
                    .uri("/docenti")
                    .bodyValue(nuovoDocente)
                    .retrieve()
                    .bodyToMono(DocenteDTO.class)
                    .block();

            System.out.println("Nuovo docente creato: " + docenteCreato);
            if (docenteCreato != null) {
                System.out.println("ID nuovo docente: " + docenteCreato.getId_docente());
            }

            return docenteCreato;
        } catch (Exception e) {
            System.err.println("ERRORE nella creazione del docente: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }



    public DocenteDTO getDocenteByNomeAndCognome(String nome, String cognome) {
        try {
            List<DocenteDTO> docenti = webClient.webClient().get()
                    .uri("/docenti?nome={nome}&cognome={cognome}", nome, cognome)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<DocenteDTO>>() {})
                    .block();

            if (docenti != null && !docenti.isEmpty()) {
                DocenteDTO primo = docenti.get(0);
                return primo;
            }
            return null;

        } catch (WebClientResponseException e) {
            System.err.println("Errore WebClient: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
            if (e.getStatusCode().value() == 404) {
                return null;
            }
            throw e;
        }
    }



    public Integer getOrCreateDocenteId(String nome, String cognome) {
        DocenteDTO docente = getOrCreateDocente(nome, cognome);
        return docente != null ? docente.getId_docente() : null; // Corretto: getId_docente()
    }


    public boolean docenteExists(Integer id_docente) {
        DocenteDTO docente = getDocenteById(id_docente);
        return docente != null;
    }

}
