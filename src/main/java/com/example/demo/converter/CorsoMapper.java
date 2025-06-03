package com.example.demo.converter;

import com.example.demo.data.DTO.CorsoDTO;
import com.example.demo.data.DTO.DocenteDTO;
import com.example.demo.data.entity.Corso;
import com.example.demo.service.DocenteService;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class CorsoMapper {

    public abstract CorsoDTO toDto(Corso corso);

    public abstract Corso toEntity(CorsoDTO dto);
}
