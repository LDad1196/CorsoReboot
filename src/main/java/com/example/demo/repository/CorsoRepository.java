package com.example.demo.repository;

import com.example.demo.data.entity.Corso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CorsoRepository extends JpaRepository<Corso, Integer> {
}
