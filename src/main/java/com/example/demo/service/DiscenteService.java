package com.example.demo.service;

import com.example.demo.configuration.WebClientConfig;
import com.example.demo.data.DTO.DiscenteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscenteService {

    @Autowired
    WebClientConfig webClient;

    public DiscenteDTO getDiscenteById(Integer id_discente) {
        return webClient.webClient().get()
                .uri("/discenti/{id_discente}", id_discente)
                .retrieve()
                .bodyToMono(DiscenteDTO.class)
                .block();
    }

    public DiscenteDTO getDiscenteByNomeAndCognome(String nome, String cognome) {
        return webClient.webClient().get()
                .uri("/discenti?nome={nome}&cognome={cognome}", nome, cognome)
                .retrieve()
                .bodyToMono(DiscenteDTO.class)
                .block();
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
