package pm.service;

import org.springframework.stereotype.Service;
import pm.entity.Project;
import pm.entity.body.ProjectBody;
import pm.repository.ProjectRepository;
import pm.repository.SubprojectRepository;
import pm.repository.TaskRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final SubprojectRepository subprojectRepository;
    private final TaskRepository taskRepository;

    public ProjectService(ProjectRepository projectRepository, SubprojectRepository subprojectRepository, TaskRepository taskRepository) {
        this.projectRepository = projectRepository;
        this.subprojectRepository = subprojectRepository;
        this.taskRepository = taskRepository;
    }

    public Optional<Project> findById(Integer id) {
        return projectRepository.findById(id);
    }

    public List<Project> findByName(String name) {
        return projectRepository.findByName(name);
    }

    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    public boolean save(ProjectBody project) {
        if (project == null || project.getName() == null || project.getName().trim().isEmpty()) {
            return false;
        }
        Project savedProject = new Project();
        savedProject.setName(project.getName());
        projectRepository.save(savedProject);
        return true;
    }

    public boolean delete(int id) {
        if (projectRepository.existsById(id)) {
            projectRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean update(ProjectBody project, int id) {
        if (projectRepository.existsById(id)) {
            Optional<Project> existsProject = projectRepository.findById(id);
            Project updatedProject = new Project();

            updatedProject.setId(id);

            if (project.getName() == null || project.getName().isEmpty() || project.getName().equals(existsProject.get().getName())) {
                updatedProject.setName(existsProject.get().getName());
            } else {
                updatedProject.setName(project.getName());
            }

            projectRepository.save(updatedProject);
            return true;
        }
        return false;
    }
}
