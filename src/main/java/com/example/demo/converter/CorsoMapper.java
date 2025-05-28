package com.example.demo.converter;

import com.example.demo.data.DTO.CorsoDTO;
import com.example.demo.data.entity.Corso;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public abstract class CorsoMapper {

    public abstract CorsoDTO toDto(Corso corso);

    public abstract Corso toEntity(CorsoDTO dto);

}
