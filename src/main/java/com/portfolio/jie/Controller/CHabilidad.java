
package com.portfolio.jie.Controller;

import com.portfolio.jie.Dto.dtoHabilidad;
import com.portfolio.jie.Entity.Habilidad;
import com.portfolio.jie.Security.Controller.Mensaje;
import com.portfolio.jie.Service.SHabilidad;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;










@RestController
@RequestMapping("/habilidad")
@CrossOrigin(origins = {"https://portfoliojie.web.app/","http://localhost:4200"})
public class CHabilidad {

    @Autowired
    SHabilidad sHabilidad;

    @GetMapping("/lista")
    public ResponseEntity<List<Habilidad>> list() {
        List<Habilidad> list = sHabilidad.list();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<Habilidad> getById(@PathVariable("id") int id) {
        if (!sHabilidad.existsById(id)) {
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        }
        Habilidad habilidad = sHabilidad.getOne(id).get();
        return new ResponseEntity(habilidad, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        if (!sHabilidad.existsById(id)) {
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        }
        sHabilidad.delete(id);
        return new ResponseEntity(new Mensaje("Skill eliminado"), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody dtoHabilidad dtohabilidad) {
        if (StringUtils.isBlank(dtohabilidad.getNombre())) {
            return new ResponseEntity(new Mensaje("El nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        }
        if (sHabilidad.existsByNombre(dtohabilidad.getNombre())) {
            return new ResponseEntity(new Mensaje("Esa skill ya existe"), HttpStatus.BAD_REQUEST);
        }

        Habilidad habilidad = new Habilidad(dtohabilidad.getNombre(), dtohabilidad.getPorcentaje());
        sHabilidad.save(habilidad);

        return new ResponseEntity(new Mensaje("Skill agregada"), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody dtoHabilidad dtohabilidad) {
        //Validamos si existe el ID
        if (!sHabilidad.existsById(id)) {
            return new ResponseEntity(new Mensaje("El ID no existe"), HttpStatus.BAD_REQUEST);
        }
        //Compara nombre de skills
        if (sHabilidad.existsByNombre(dtohabilidad.getNombre()) && sHabilidad.getByNombre(dtohabilidad.getNombre()).get()
                .getId() != id) {
            return new ResponseEntity(new Mensaje("Esa skill ya existe"), HttpStatus.BAD_REQUEST);
        }
        //No puede estar vacio
        if (StringUtils.isBlank(dtohabilidad.getNombre())) {
            return new ResponseEntity(new Mensaje("El nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        }

        Habilidad habilidad = sHabilidad.getOne(id).get();
        habilidad.setNombre(dtohabilidad.getNombre());
        habilidad.setPorcentaje(dtohabilidad.getPorcentaje());

        sHabilidad.save(habilidad);
        return new ResponseEntity(new Mensaje("Skill actualizada"), HttpStatus.OK);

    }
}
