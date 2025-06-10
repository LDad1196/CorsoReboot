package com.example.demo.data.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CorsoDTO {

    private String nome;

    private String anno_accademico;

    private Integer id_docente;

    private String nomeDocente;

    private String cognomeDocente;

    private DocenteDTO docente;

    private List<DiscenteDTO> discenti;

    private Integer numero_discenti;

    private List<DiscenteDTO> nuoviDiscenti;

    public String getAnno_accademico() { return anno_accademico; }

    public void setAnno_accademico(String anno_accademico) { this.anno_accademico = anno_accademico; }

    public String getNome() { return nome; }

    public void setNome(String nome) { this.nome = nome; }

    public Integer getId_docente() { return id_docente; }

    public void setId_docente(Integer id_docente) { this.id_docente = id_docente; }

    public String getNomeDocente() { return nomeDocente; }

    public void setNomeDocente(String nomeDocente) { this.nomeDocente = nomeDocente; }

    public String getCognomeDocente() { return cognomeDocente; }

    public void setCognomeDocente(String cognomeDocente) { this.cognomeDocente = cognomeDocente; }

    public DocenteDTO getDocente() { return docente; }

    public void setDocente(DocenteDTO docente) { this.docente = docente; }

    public List<DiscenteDTO> getDiscenti() { return discenti; }

    public void setDiscenti(List<DiscenteDTO> discenti) { this.discenti = discenti; }

    public Integer getNumero_discenti() { return numero_discenti; }

    public void setNumero_discenti(Integer numero_discenti) { this.numero_discenti = numero_discenti; }

    public List<DiscenteDTO> getNuoviDiscenti() { return nuoviDiscenti; }

    public void setNuoviDiscenti(List<DiscenteDTO> nuoviDiscenti) { this.nuoviDiscenti = nuoviDiscenti; }

}
