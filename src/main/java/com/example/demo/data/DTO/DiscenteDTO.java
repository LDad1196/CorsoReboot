package com.example.demo.data.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DiscenteDTO {
    private Integer id_discente;
    private String nome;
    private String cognome;

    public Integer getId_discente() {
        return id_discente;
    }

    public void setId_discente(Integer id_discente) {
        this.id_discente = id_discente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }
}
