package com.example.demo.converter;

import com.example.demo.configuration.OpenFeignConfig;
import com.example.demo.data.DTO.DocenteDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(
        name = "docente-service",
        url = "http://localhost:8080",
        configuration = OpenFeignConfig.class
)
public interface DocenteClient {

    @GetMapping("/docenti/{id}")
    DocenteDTO getDocenteById(@PathVariable("id") Integer id);

}

