package com.example.demo.controller.api;

import com.example.demo.data.DTO.CorsoDTO;
import com.example.demo.service.CorsoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/corsi")
public class CorsoApiController {

    @Autowired
    private CorsoService corsoService;

    // Accesso per utenti autenticati (USER o ADMIN)
    @GetMapping("/list")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public Iterable<CorsoDTO> list(@AuthenticationPrincipal Jwt jwt) {
        // Log dell'utente autenticato (opzionale)
        System.out.println("Accesso effettuato da: " + jwt.getSubject());
        return corsoService.findAll();
    }

    @GetMapping("/{id_corso}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public CorsoDTO getById(@PathVariable Integer id_corso) {
        return corsoService.findById(id_corso);
    }

    // Solo gli ADMIN possono creare corsi
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public CorsoDTO create(@RequestBody CorsoDTO corso) {
        return corsoService.save(corso);
    }

    @PutMapping("/{id_corso}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public CorsoDTO update(@PathVariable Integer id_corso,
                           @RequestBody CorsoDTO corso) {
        return corsoService.update(id_corso, corso);
    }

    @DeleteMapping("/{id_corso}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void delete(@PathVariable Integer id_corso) {
        corsoService.delete(id_corso);
    }
}
