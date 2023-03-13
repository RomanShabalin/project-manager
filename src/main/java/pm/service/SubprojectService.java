package pm.service;

import org.springframework.stereotype.Service;
import pm.entity.Project;
import pm.entity.Subproject;
import pm.entity.body.SubprojectBody;
import pm.repository.ProjectRepository;
import pm.repository.SubprojectRepository;
import pm.repository.TaskRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SubprojectService {
    private final SubprojectRepository subprojectRepository;
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;

    public SubprojectService(SubprojectRepository subprojectRepository, ProjectRepository projectRepository, TaskRepository taskRepository) {
        this.subprojectRepository = subprojectRepository;
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
    }

    public Optional<Subproject> findById(Integer id) {
        return subprojectRepository.findById(id);
    }

    public List<Subproject> findByName(String name) {
        return subprojectRepository.findByName(name);
    }

    public List<Subproject> findAll() {
        return subprojectRepository.findAll();
    }

    public boolean save(SubprojectBody subproject) {
        if (subproject == null || subproject.getName() == null || subproject.getName().trim().isEmpty()) {
            return false;
        }

        Optional<Project> project = projectRepository.findById(subproject.getProjectId());
        if (project.isEmpty()) {
            return false;
        }

        Subproject savedSubproject = new Subproject();
        savedSubproject.setName(subproject.getName());
        savedSubproject.setProject(project.get());
        subprojectRepository.save(savedSubproject);
        return true;
    }

    public boolean delete(int id) {
        if (subprojectRepository.existsById(id)) {
            subprojectRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean update(SubprojectBody subproject, int id) {
        if (subprojectRepository.existsById(id)) {
            Optional<Subproject> existsSubproject = subprojectRepository.findById(id);
            Subproject updatedSubproject = new Subproject();

            updatedSubproject.setId(id);

            if (subproject.getName() == null || subproject.getName().isEmpty() || subproject.getName().equals(existsSubproject.get().getName())) {
                updatedSubproject.setName(existsSubproject.get().getName());
            } else {
                updatedSubproject.setName(subproject.getName());
            }

            if (subproject.getProjectId() != null) {
                Optional<Project> project = projectRepository.findById(subproject.getProjectId());
                if (project.isPresent()) {
                    updatedSubproject.setProject(project.get());
                } else {
                    updatedSubproject.setProject(existsSubproject.get().getProject());
                }
            } else {
                updatedSubproject.setProject(existsSubproject.get().getProject());
            }

            subprojectRepository.save(updatedSubproject);
            return true;
        }
        return false;
    }
}
