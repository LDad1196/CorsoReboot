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
    public Iterable<CorsoDTO> list(@RequestHeader(value = "username") String username) {
        System.out.println(">>>> CALL /corsi/list — username = " + username);
        corsoService.checkAuthorization(username, false);
        return corsoService.findAll();
    }

    @GetMapping("/{id_corso}")
    public CorsoDTO getById(@PathVariable("id_corso") Integer id_corso,
                            @RequestHeader("username") String username) {
        corsoService.checkAuthorization(username, false);
        return corsoService.findById(id_corso);
    }

    @PostMapping
    public CorsoDTO create(@RequestHeader("username") String username,
                           @RequestBody CorsoDTO corso) {
        corsoService.checkAuthorization(username, true);
        return corsoService.save(corso);
    }

    @PutMapping("/{id_corso}")
    public CorsoDTO update(@PathVariable("id_corso") Integer id_corso,
                           @RequestHeader("username") String username,
                           @RequestBody CorsoDTO corso) {
        corsoService.checkAuthorization(username, true);
        return corsoService.update(id_corso, corso);
    }

    @DeleteMapping("/{id_corso}")
    public void delete(@PathVariable("id_corso") Integer id_corso,
                       @RequestHeader("username") String username) {
        corsoService.checkAuthorization(username, true);
        corsoService.delete(id_corso);
    }

    @PostMapping("/{id_corso}/discenti/{id_discente}")
    public CorsoDTO iscriviDiscente(@PathVariable("id_corso") Integer id_corso,
                                    @PathVariable("id_discente") Integer id_discente,
                                    @RequestHeader("username") String username) {
        corsoService.checkAuthorization(username, true);
        return corsoService.iscriviDiscente(id_corso, id_discente);
    }
}
