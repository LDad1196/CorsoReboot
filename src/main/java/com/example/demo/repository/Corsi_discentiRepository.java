package com.example.demo.repository;

import com.example.demo.data.entity.Corsi_discenti;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface Corsi_discentiRepository extends JpaRepository<Corsi_discenti, Integer> {

    @Query("SELECT c FROM Corsi_discenti c WHERE c.id_corso = :id_corso")
    List<Corsi_discenti> findById_corso(@Param("id_corso") Integer id_corso);

    @Query("SELECT c FROM Corsi_discenti c WHERE c.id_discente = :id_discente")
    List<Corsi_discenti> findById_discente(@Param("id_discente") Integer id_discente);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Corsi_discenti c WHERE c.id_corso = :id_corso AND c.id_discente = :id_discente")
    boolean existsById_corsoAndId_discente(@Param("id_corso") Integer id_corso, @Param("id_discente") Integer id_discente);

    @Query("SELECT COUNT(c) FROM Corsi_discenti c WHERE c.id_corso = :id_corso")
    Integer countDiscentiById_corso(@Param("id_corso") Integer id_corso);

}
