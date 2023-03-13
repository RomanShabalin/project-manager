package pm.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pm.entity.Project;
import pm.entity.body.ProjectBody;
import pm.service.ProjectService;

import java.util.List;
import java.util.Optional;

@RestController
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("/project/create")
    public ResponseEntity<?> createProject(@RequestBody ProjectBody project) {
        boolean created = projectService.save(project);
        return created ? new ResponseEntity<>(HttpStatus.CREATED) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping("/project/delete/id={id}")
    public ResponseEntity<?> deleteProject(@PathVariable(name = "id") int id) {
        boolean deleted = projectService.delete(id);
        return deleted ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PostMapping("/project/update/id={id}")
    public ResponseEntity<?> updateProject(@RequestBody ProjectBody project, @PathVariable(name = "id") int id) {
        boolean updated = projectService.update(project, id);
        return updated ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @GetMapping("/project/getById/id={id}")
    public ResponseEntity<Optional<Project>> getProjectById(@PathVariable(name = "id") Integer id) {
        Optional<Project> project = projectService.findById(id);
        return project.isEmpty() ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(project, HttpStatus.OK);
    }

    @GetMapping("/project/getByName/name={name}")
    public ResponseEntity<List<Project>> getProjectByName(@PathVariable(name = "name") String name) {
        List<Project> projects = projectService.findByName(name);
        return projects.isEmpty() ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/project/getAll")
    public ResponseEntity<List<Project>> getAllProjects() {
        List<Project> projects = projectService.findAll();
        return projects == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(projects, HttpStatus.OK);
    }
}
