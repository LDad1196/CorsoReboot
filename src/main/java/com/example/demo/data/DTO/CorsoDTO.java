package com.example.demo.data.DTO;

public class CorsoDTO {

    private String nome;

    private String anno_accademico;

    private Integer id_docente;

    private DocenteDTO docente;

    public String getAnno_accademico() {
        return anno_accademico;
    }

    public void setAnno_accademico(String anno_accademico) {
        this.anno_accademico = anno_accademico;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getId_docente() {
        return id_docente;
    }

    public void setId_docente(Integer id_docente) {
        this.id_docente = id_docente;
    }

    public DocenteDTO getDocente() {
        return docente;
    }

    public void setDocente(DocenteDTO docente) {
        this.docente = docente;
    }
}
