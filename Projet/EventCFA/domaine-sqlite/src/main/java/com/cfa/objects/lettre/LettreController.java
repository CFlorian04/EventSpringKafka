package com.cfa.objects.lettre;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Date;

@RestController
@RequestMapping(path = "/lettre")
@RequiredArgsConstructor
public class LettreController {

    private final LettreRepository repository;

    @GetMapping("/getAll")
    public Collection<Lettre> getAll() { return repository.findAll(); }

    @GetMapping("/getById{id}")
    public Lettre getById(@PathVariable(value = "id") final Integer id) { return repository.findById(id).orElse(null);}

}
