package com.example.demo.data.entity;

import jakarta.persistence.*;
@Entity
@Table(name = "corsi_discenti")
public class Corsi_discenti {

    @Id  // AGGIUNTO - Serve un ID primario
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "id_corso")
    private Integer id_corso;

    @Column(name = "id_discente")
    private Integer id_discente;

    public Corsi_discenti(Integer id_corso, Integer id_discente) {
        this.id_corso = id_corso;
        this.id_discente = id_discente;
    }

    public Corsi_discenti() {}

    public Integer getId_corso() {
        return id_corso;
    }

    public void setId_corso(Integer id_corso) {
        this.id_corso = id_corso;
    }

    public Integer getId_discente() {
        return id_discente;
    }

    public void setId_discente(Integer id_discente) {
        this.id_discente = id_discente;
    }
}
