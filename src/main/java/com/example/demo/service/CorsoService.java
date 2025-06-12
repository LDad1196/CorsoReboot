
package com.example.demo.service;

import com.example.demo.converter.CorsoMapper;
import com.example.demo.data.DTO.CorsoDTO;
import com.example.demo.data.DTO.DocenteDTO;
import com.example.demo.data.DTO.DiscenteDTO;
import com.example.demo.data.DTO.UserDTO;
import com.example.demo.data.entity.Corso;
import com.example.demo.repository.CorsoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

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

    @Autowired
    DiscenteService discenteService;

    @Autowired
    @Qualifier("utentiWebClient")
    WebClient utentiWebClient;

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
        // GESTIONE DOCENTE - Priorità ai nomi se presenti
        if (corsoDTO.getNomeDocente() != null && corsoDTO.getCognomeDocente() != null) {
            DocenteDTO docente = docenteService.getOrCreateDocente(
                    corsoDTO.getNomeDocente(),
                    corsoDTO.getCognomeDocente()
            );
            if (docente != null && docente.getId_docente() != null) {
                corsoDTO.setId_docente(docente.getId_docente());
            } else {
                throw new IllegalArgumentException("Impossibile trovare o creare il docente: " +
                        corsoDTO.getNomeDocente() + " " + corsoDTO.getCognomeDocente());
            }
        }
        // Se non ci sono nomi ma c'è l'ID, valida l'ID
        else if (corsoDTO.getId_docente() != null) {
            if (!docenteService.docenteExists(corsoDTO.getId_docente())) {
                throw new IllegalArgumentException("Docente non trovato con ID: " + corsoDTO.getId_docente());
            }
        }

        Corso corso = corsoMapper.toEntity(corsoDTO);
        corso = corsoRepository.save(corso);

        if (corsoDTO.getNuoviDiscenti() != null && !corsoDTO.getNuoviDiscenti().isEmpty()) {
            processaNuoviDiscenti(corso.getId_corso(), corsoDTO.getNuoviDiscenti());
        }

        return convertToDto(corso);
    }

    public CorsoDTO update(Integer id_corso, CorsoDTO corsoDTO) {
        Corso corso = corsoRepository.findById(id_corso)
                .orElseThrow(() -> new IllegalArgumentException("Corso non trovato"));

        // Aggiorna nome corso se presente
        if (corsoDTO.getNome() != null) {
            corso.setNome(corsoDTO.getNome());
        }

        // Aggiorna anno accademico se presente
        if (corsoDTO.getAnno_accademico() != null) {
            corso.setAnno_accademico(corsoDTO.getAnno_accademico());
        }

        // GESTIONE DOCENTE - Priorità ai nomi se presenti
        if (corsoDTO.getNomeDocente() != null && corsoDTO.getCognomeDocente() != null) {
            DocenteDTO docente = docenteService.getOrCreateDocente(
                    corsoDTO.getNomeDocente(),
                    corsoDTO.getCognomeDocente()
            );
            if (docente != null && docente.getId_docente() != null) {
                corso.setId_docente(docente.getId_docente());
            } else {
                throw new IllegalArgumentException("Impossibile trovare o creare il docente: " +
                        corsoDTO.getNomeDocente() + " " + corsoDTO.getCognomeDocente());
            }
        }
        // Se non ci sono nomi, usa l'ID se presente
        else if (corsoDTO.getId_docente() != null) {
            if (!docenteService.docenteExists(corsoDTO.getId_docente())) {
                throw new IllegalArgumentException("Docente non trovato con ID: " + corsoDTO.getId_docente());
            }
            corso.setId_docente(corsoDTO.getId_docente());
        }

        corso = corsoRepository.save(corso);

        if (corsoDTO.getNuoviDiscenti() != null && !corsoDTO.getNuoviDiscenti().isEmpty()) {

            processaNuoviDiscenti(corso.getId_corso(), corsoDTO.getNuoviDiscenti());
        }

        return convertToDto(corso);
    }

    // Metodo per processare i nuovi discenti con gestione più robusta
    private void processaNuoviDiscenti(Integer id_corso, List<DiscenteDTO> nuoviDiscenti) {

        for (int i = 0; i < nuoviDiscenti.size(); i++) {
            DiscenteDTO discenteInput = nuoviDiscenti.get(i);

            if (discenteInput.getNome() != null && discenteInput.getCognome() != null) {
                try {
                    DiscenteDTO discente = discenteService.getOrCreateDiscente(
                            discenteInput.getNome(),
                            discenteInput.getCognome()
                    );

                    if (discente != null && discente.getId_discente() != null) {
                        try {
                            corsi_discentiService.iscriviDiscenteDiretto(id_corso, discente.getId_discente());
                        } catch (Exception e) {
                            System.err.println("ERRORE nell'iscrizione diretta, provo con metodo standard...");
                            // Fallback al metodo standard
                            corsi_discentiService.iscriviDiscente(id_corso, discente.getId_discente());
                        }
                    } else {
                        System.err.println("ERRORE: Impossibile ottenere ID per il discente: " +
                                discenteInput.getNome() + " " + discenteInput.getCognome());
                    }
                } catch (Exception e) {
                    System.err.println("ERRORE nell'iscrizione del discente: " +
                            discenteInput.getNome() + " " + discenteInput.getCognome() +
                            " - " + e.getMessage());
                    e.printStackTrace();
                    // Continua con il prossimo discente invece di interrompere tutto
                }
            } else {
                System.err.println("ERRORE: Nome o cognome mancanti per il discente: " + discenteInput);
            }
        }
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
        if (!corsoRepository.existsById(id_corso)) {
            throw new IllegalArgumentException("Corso non trovato con ID: " + id_corso);
        }
        if (!discenteService.discenteExists(id_discente)) {
            throw new IllegalArgumentException("Discente non trovato con ID: " + id_discente);
        }
        corsi_discentiService.iscriviDiscente(id_corso, id_discente);
        return findById(id_corso);
    }


    public UserDTO getUserInfoByUsername(String username) {
        try {
            return utentiWebClient.get()
                    .uri("/users/by-username/{username}", username)
                    .retrieve()
                    .bodyToMono(UserDTO.class)
                    .block();
        } catch (WebClientResponseException ex) {
            System.err.println("Errore HTTP: " + ex.getStatusCode());
            System.err.println("Corpo risposta: " + ex.getResponseBodyAsString());
            throw new SecurityException("Utente non trovato");
        }
    }

    public void checkAuthorization(String username, boolean scritturaRichiesta) {
        UserDTO user = getUserInfoByUsername(username);

        if (user == null) {
            throw new SecurityException("Utente non trovato");
        }

        System.out.println("Utente trovato: " + user.getUsername() + ", ruolo: " + user.getRole());
        String role = user.getRole().toUpperCase();

        if ("ADMIN".equals(role)) return;

        if ("USER".equals(role) && scritturaRichiesta) {
            throw new SecurityException("Lo USER può solo visualizzare");
        }

        if (!"USER".equals(role) && !"ADMIN".equals(role)) {
            throw new SecurityException("Ruolo non autorizzato: " + role);
        }
    }


}
