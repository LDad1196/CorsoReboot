package com.example.demo.service;

import com.example.demo.data.DTO.UserDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
public class AuthService {

    private final WebClient authWebClient;

    public AuthService(@Qualifier("authWebClient") WebClient authWebClient) {
        this.authWebClient = authWebClient;
    }

    public UserDTO validateUser(String username, String password) {
        try {
            return authWebClient.get()
                    .uri("/users/validate")
                    .headers(headers -> headers.setBasicAuth(username, password))
                    .retrieve()
                    .bodyToMono(UserDTO.class)
                    .block();
        } catch (WebClientResponseException e) {
            System.err.println("Errore validazione utente: " + e.getStatusCode());
            return null;
        }
    }

    public UserDTO getUserByUsername(String username) {
        try {
            return authWebClient.get()
                    .uri("/users/by-username/{username}", username)
                    .retrieve()
                    .bodyToMono(UserDTO.class)
                    .block();
        } catch (WebClientResponseException e) {
            System.err.println("Errore recupero utente: " + e.getStatusCode());
            return null;
        }
    }
}