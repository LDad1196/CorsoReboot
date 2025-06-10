
package com.example.demo.service;

import com.example.demo.configuration.WebClientConfig;
import com.example.demo.data.DTO.DiscenteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;

@Service
public class DiscenteService {

    @Autowired
    WebClientConfig webClient;

    public DiscenteDTO getDiscenteById(Integer id_discente) {
        try {
            return webClient.webClient().get()
                    .uri("/discenti/{id_discente}", id_discente)
                    .retrieve()
                    .bodyToMono(DiscenteDTO.class)
                    .block();
        } catch (Exception e) {
            System.err.println("Errore nel recupero discente per ID: " + e.getMessage());
            return null;
        }
    }

    // NUOVO: Metodo per ottenere tutti i discenti e filtrarli localmente
    public List<DiscenteDTO> getAllDiscenti() {
        try {
            return webClient.webClient().get()
                    .uri("/discenti")
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<DiscenteDTO>>() {})
                    .block();

        } catch (WebClientResponseException e) {
            System.err.println("Errore WebClient discenti: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
            return List.of(); // Restituisce lista vuota invece di null
        } catch (Exception e) {
            System.err.println("Errore generico nell'ottenere tutti i discenti: " + e.getMessage());
            return List.of();
        }
    }

    // AGGIORNATO: Cerca discente filtrando la lista completa
    public DiscenteDTO getDiscenteByNomeAndCognome(String nome, String cognome) {
        try {
            List<DiscenteDTO> tuttiDiscenti = getAllDiscenti();
            DiscenteDTO discenteTrovato = tuttiDiscenti.stream()
                    .filter(d -> d.getNome() != null && d.getCognome() != null)
                    .filter(d -> d.getNome().equalsIgnoreCase(nome.trim()) &&
                            d.getCognome().equalsIgnoreCase(cognome.trim()))
                    .findFirst()
                    .orElse(null);
            return discenteTrovato;
        } catch (Exception e) {
            System.err.println("Errore nella ricerca locale del discente: " + e.getMessage());
            return null;
        }
    }

    public DiscenteDTO getOrCreateDiscente(String nome, String cognome) {
        DiscenteDTO discenteEsistente = getDiscenteByNomeAndCognome(nome, cognome);
        if (discenteEsistente != null) {
            return discenteEsistente;
        }
        DiscenteDTO nuovoDiscente = new DiscenteDTO();
        nuovoDiscente.setNome(nome);
        nuovoDiscente.setCognome(cognome);

        try {
            DiscenteDTO discenteCreato = webClient.webClient().post()
                    .uri("/discenti")
                    .bodyValue(nuovoDiscente)
                    .retrieve()
                    .bodyToMono(DiscenteDTO.class)
                    .block();
            return discenteCreato;
        } catch (Exception e) {
            System.err.println("ERRORE nella creazione del discente: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public boolean discenteExists(Integer id_discente) {
        DiscenteDTO discente = getDiscenteById(id_discente);
        return discente != null;
    }

    public List<DiscenteDTO> getDiscentiByIds(List<Integer> ids_discenti) {
        if (ids_discenti == null || ids_discenti.isEmpty()) {
            return List.of();
        }

        return ids_discenti.stream()
                .map(this::getDiscenteById)
                .filter(discente -> discente != null)
                .toList();
    }
}