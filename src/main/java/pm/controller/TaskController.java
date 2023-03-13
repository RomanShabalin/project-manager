package pm.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pm.entity.Task;
import pm.entity.body.TaskBody;
import pm.service.TaskService;

import java.util.List;
import java.util.Optional;

@RestController
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/task/create")
    public ResponseEntity<?> createTask(@RequestBody TaskBody task) {
        boolean created = taskService.save(task);
        return created ? new ResponseEntity<>(HttpStatus.CREATED) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping("/task/delete/id={id}")
    public ResponseEntity<?> deleteTask(@PathVariable(name = "id") int id) {
        boolean deleted = taskService.delete(id);
        return deleted ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PostMapping("/task/update/id={id}")
    public ResponseEntity<?> updateTask(@RequestBody TaskBody task, @PathVariable(name = "id") int id) {
        boolean updated = taskService.update(task, id);
        return updated ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @GetMapping("/task/getById/id={id}")
    public ResponseEntity<Optional<Task>> getTaskById(@PathVariable(name = "id") Integer id) {
        Optional<Task> task = taskService.findById(id);
        return task.isEmpty() ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(task, HttpStatus.OK);
    }

    @GetMapping("/task/getByName/name={name}")
    public ResponseEntity<List<Task>> getTaskByName(@PathVariable(name = "name") String name) {
        List<Task> tasks = taskService.findByName(name);
        return tasks.isEmpty() ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/task/getAll")
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskService.findAll();
        return tasks == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(tasks, HttpStatus.OK);
    }
}
