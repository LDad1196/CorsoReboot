package com.example.demo.controller.api;

import com.example.demo.data.DTO.CorsoDTO;
import com.example.demo.service.CorsoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/corsi")
public class CorsoApiController {

    @Autowired
    CorsoService corsoService;

    @GetMapping("/list")
    public Iterable<CorsoDTO> list () {
        return corsoService.findAll();
    }

    @GetMapping("/{id_corso}")
    public CorsoDTO getById(@PathVariable("id_corso") Integer id_corso) {
        return corsoService.findById(id_corso);
    }

    @PostMapping
    public CorsoDTO create(@RequestBody CorsoDTO corso) {
        return (CorsoDTO) corsoService.save(corso);
    }


    @PutMapping("/{id_corso}")
    public CorsoDTO update(@PathVariable("id_corso") Integer id_corso,
                                   @RequestBody CorsoDTO corso) {
        return corsoService.update(id_corso, corso);
    }


    @DeleteMapping("{id_corso}")
    public void delete(@PathVariable("id_corso") Integer id_corso) {
        corsoService.delete(id_corso);
    }

    @PostMapping("/{id_corso}/discenti/{id_discente}")
    public CorsoDTO iscriviDiscente(@PathVariable("id_corso") Integer id_corso,
                                    @PathVariable("id_discente") Integer id_discente) {
        return corsoService.iscriviDiscente(id_corso, id_discente);
    }


}
