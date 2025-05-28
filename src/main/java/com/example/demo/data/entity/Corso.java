package com.example.demo.data.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "corso")
public class Corso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_corso")
    private Integer id_corso;

    @Column(name = "nome")
    private String nome;

    @Column(name = "anno_accademico")
    private String anno_accademico;

    public Corso() {}

    public Corso(String nome, String anno_accademico) {
        this.nome = nome;
        this.anno_accademico = anno_accademico;
    }

    public void setId_corso(Integer id_corso) {
        this.id_corso = id_corso;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getId_corso() {
        return id_corso;
    }

    public String getNome() {
        return nome;
    }

    public void setAnno_accademico(String anno_accademico) {
        this.anno_accademico = anno_accademico;
    }

    public String getAnno_accademico() {
        return anno_accademico;
    }

}
