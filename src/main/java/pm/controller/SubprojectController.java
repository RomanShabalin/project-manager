package pm.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pm.entity.Subproject;
import pm.entity.body.SubprojectBody;
import pm.service.SubprojectService;

import java.util.List;
import java.util.Optional;

@RestController
public class SubprojectController {
    private final SubprojectService subprojectService;

    public SubprojectController(SubprojectService subprojectService) {
        this.subprojectService = subprojectService;
    }

    @PostMapping("/subproject/create")
    public ResponseEntity<?> createSubproject(@RequestBody SubprojectBody subproject) {
        boolean created = subprojectService.save(subproject);
        return created ? new ResponseEntity<>(HttpStatus.CREATED) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping("/subproject/delete/id={id}")
    public ResponseEntity<?> deleteSubproject(@PathVariable(name = "id") int id) {
        boolean deleted = subprojectService.delete(id);
        return deleted ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PostMapping("/subproject/update/id={id}")
    public ResponseEntity<?> updateSubproject(@RequestBody SubprojectBody subProject, @PathVariable(name = "id") int id) {
        boolean updated = subprojectService.update(subProject, id);
        return updated ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @GetMapping("/subproject/getById/id={id}")
    public ResponseEntity<Optional<Subproject>> getSubprojectById(@PathVariable(name = "id") Integer id) {
        Optional<Subproject> subproject = subprojectService.findById(id);
        return subproject.isEmpty() ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(subproject, HttpStatus.OK);
    }

    @GetMapping("/subproject/getByName/name={name}")
    public ResponseEntity<List<Subproject>> getSubprojectByName(@PathVariable(name = "name") String name) {
        List<Subproject> subprojects = subprojectService.findByName(name);
        return subprojects.isEmpty() ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(subprojects, HttpStatus.OK);
    }

    @GetMapping("/subproject/getAll")
    public ResponseEntity<List<Subproject>> getAllSubprojects() {
        List<Subproject> subprojects = subprojectService.findAll();
        return subprojects == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(subprojects, HttpStatus.OK);
    }
}
