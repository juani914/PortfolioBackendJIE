package com.portfolio.jie.Controller;

import com.portfolio.jie.Dto.dtoPersona;
import com.portfolio.jie.Entity.Persona;
import com.portfolio.jie.Security.Controller.Mensaje;
import com.portfolio.jie.Service.ImpPersonaService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/personas")
@CrossOrigin(origins = {"https://jiefrontend.web.app","http://localhost:4200"})
public class PersonaController {

    @Autowired
    ImpPersonaService personaService;

    @GetMapping("/lista")
    public ResponseEntity<List<Persona>> list() {
        List<Persona> list = personaService.list();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    
    @GetMapping("/detail/{id}")
    public ResponseEntity<Persona> getById(@PathVariable("id") int id) {
        if (!personaService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        
        Optional<Persona> persona = personaService.getOne(id);
        if (persona.isPresent()) {
            return ResponseEntity.ok(persona.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Mensaje> delete(@PathVariable("id") int id) {
        if (!personaService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        personaService.delete(id);
        return ResponseEntity.ok(new Mensaje("Persona eliminada"));
    }

    @PostMapping("/crear")
    public ResponseEntity<Mensaje> create(@RequestBody dtoPersona dtopersona) {
        if (StringUtils.isBlank(dtopersona.getNombre())) {
            return ResponseEntity.badRequest().body(new Mensaje("El nombre es obligatorio"));
        }
        if (personaService.existsByNombre(dtopersona.getNombre())) {
            return ResponseEntity.badRequest().body(new Mensaje("Ese nombre ya existe"));
        }
        
        Persona persona = new Persona();
        persona.setNombre(dtopersona.getNombre());
        persona.setApellido(dtopersona.getApellido());
        persona.setDescripcion(dtopersona.getDescripcion());
        persona.setImg(dtopersona.getImg());
        
        personaService.save(persona);
        
        return ResponseEntity.ok(new Mensaje("Persona creada"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Mensaje> update(@PathVariable("id") int id, @RequestBody dtoPersona dtopersona) {
        if (!personaService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        if (personaService.existsByNombre(dtopersona.getNombre()) && personaService.getByNombre(dtopersona.getNombre()).get().getId() != id) {
            return ResponseEntity.badRequest().body(new Mensaje("Ese nombre ya existe"));
        }
        if (StringUtils.isBlank(dtopersona.getNombre())) {
            return ResponseEntity.badRequest().body(new Mensaje("El campo no puede estar vacio"));
        }
        
        Optional<Persona> persona = personaService.getOne(id);
        if (persona.isPresent()) {
            Persona personaToUpdate = persona.get();
            personaToUpdate.setNombre(dtopersona.getNombre());
            personaToUpdate.setApellido(dtopersona.getApellido());
            personaToUpdate.setDescripcion(dtopersona.getDescripcion());
            personaToUpdate.setImg(dtopersona.getImg());
            
            personaService.save(personaToUpdate);
            
            return ResponseEntity.ok(new Mensaje("Persona actualizada"));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/traer/perfil/{nombre}")
    public ResponseEntity<Persona> getPerfil(@PathVariable("nombre") String nombre) {
        Optional<Persona> perfil = personaService.getPerfil(nombre);
        if (perfil.isPresent()) {
            return ResponseEntity.ok(perfil.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
