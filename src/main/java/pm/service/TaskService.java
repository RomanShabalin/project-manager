package pm.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pm.config.SecurityUser;
import pm.entity.Users;
import pm.entity.*;
import pm.entity.body.TaskBody;
import pm.repository.ProjectRepository;
import pm.repository.SubprojectRepository;
import pm.repository.TaskRepository;
import pm.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final SubprojectRepository subprojectRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, ProjectRepository projectRepository, SubprojectRepository subprojectRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.subprojectRepository = subprojectRepository;
        this.userRepository = userRepository;
    }

    public Optional<Task> findById(Integer id) {
        return taskRepository.findById(id);
    }

    public List<Task> findByName(String name) {
        return taskRepository.findByName(name);
    }

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public boolean save(TaskBody task) {
        Task savedTask = new Task();
        Optional<Project> project;
        Optional<Subproject> subproject;
        if (task == null || task.getName() == null || task.getName().trim().isEmpty()
                || task.getInfo() == null || task.getInfo().trim().isEmpty()
                || task.getType() == null || task.getType().trim().isEmpty()) {
            return false;
        } else {
            if ((task.getProjectId() == null && task.getSubprojectId() == null) ||
                (task.getProjectId() != null && task.getSubprojectId() != null)) {
                return false;
            }

            if (task.getProjectId() != null) {
                project = projectRepository.findById(task.getProjectId());

                if (project.isEmpty()) {
                    return false;
                }
            } else {
                project = projectRepository.findById(0);
            }

            if (task.getSubprojectId() != null) {
                subproject = subprojectRepository.findById(task.getSubprojectId());

                if (subproject.isEmpty()) {
                    return false;
                }
            } else {
                subproject = subprojectRepository.findById(0);
            }

            try {
                if (TaskTypeEnum.valueOf(task.getType()).equals(TaskTypeEnum.MANAGER) || TaskTypeEnum.valueOf(task.getType()).equals(TaskTypeEnum.TECHSPECIALIST)) {
                    savedTask.setType(TaskTypeEnum.valueOf(task.getType()));
                } else {
                    savedTask.setType(TaskTypeEnum.UNKNOWN);
                }
            } catch (Exception e) {
                savedTask.setType(TaskTypeEnum.UNKNOWN);
            }
        }

        savedTask.setName(task.getName());
        savedTask.setStatus(TaskStatusEnum.NEW);
        savedTask.setCreateDate(LocalDate.now());
        savedTask.setModifiedDateStatus(null);
        savedTask.setInfo(task.getInfo());
        if (subproject.isPresent()) {
            savedTask.setSubproject(subproject.get());
        } else {
            savedTask.setSubproject(null);
        }
        if (project.isPresent()) {
            savedTask.setProject(project.get());
        } else {
            savedTask.setProject(null);
        }

        savedTask.setUsers(this.getCurrentUser());

        taskRepository.save(savedTask);
        return true;
    }

    public boolean delete(int id) {
        Users user = this.getCurrentUser();
        if (this.isRoleOnlyUser(user)) {
            Task task = taskRepository.findByUsersAndId(user, id);
            if (task != null) {
                if (taskRepository.existsById(id)) {
                    taskRepository.deleteById(id);
                    return true;
                }
            }
            return false;
        }

        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean update(TaskBody task, int id) {
        if (taskRepository.existsById(id)) {
            Optional<Task> existsTask = taskRepository.findById(id);
            Task updatedTask = new Task();

            if (this.isRoleOnlyUser(this.getCurrentUser())) {
                updatedTask.setId(id);
                updatedTask.setName(existsTask.get().getName());

                try {
                    if (task.getStatus() == null || task.getStatus().isEmpty() || TaskStatusEnum.valueOf(task.getStatus()).equals(existsTask.get().getStatus())) {
                        updatedTask.setStatus(existsTask.get().getStatus());
                    } else {
                        updatedTask.setStatus(TaskStatusEnum.valueOf(task.getStatus()));
                    }
                } catch (Exception e) {
                    updatedTask.setStatus(TaskStatusEnum.UNKNOWN);
                }

                updatedTask.setCreateDate(existsTask.get().getCreateDate());
                updatedTask.setModifiedDateStatus(LocalDate.now());
                updatedTask.setInfo(existsTask.get().getInfo());
                updatedTask.setType(existsTask.get().getType());
                updatedTask.setProject(existsTask.get().getProject() == null ? null : existsTask.get().getProject());
                updatedTask.setSubproject(existsTask.get().getSubproject() == null ? null : existsTask.get().getSubproject());
                updatedTask.setUsers(existsTask.get().getUsers());

                taskRepository.save(updatedTask);
                return true;
            } else {
                updatedTask.setId(id);

                if (task.getName() == null || task.getName().isEmpty() || task.getName().equals(existsTask.get().getName())) {
                    updatedTask.setName(existsTask.get().getName());
                } else {
                    updatedTask.setName(task.getName());
                }

                try {
                    if (task.getStatus() == null || task.getStatus().isEmpty() || TaskStatusEnum.valueOf(task.getStatus()).equals(existsTask.get().getStatus())) {
                        updatedTask.setStatus(existsTask.get().getStatus());
                    } else {
                        updatedTask.setStatus(TaskStatusEnum.valueOf(task.getStatus()));
                    }
                } catch (Exception e) {
                    updatedTask.setStatus(TaskStatusEnum.UNKNOWN);
                }

                updatedTask.setCreateDate(existsTask.get().getCreateDate());
                updatedTask.setModifiedDateStatus(LocalDate.now());

                if (task.getInfo() == null || task.getInfo().isEmpty() || task.getInfo().equals(existsTask.get().getInfo())) {
                    updatedTask.setInfo(existsTask.get().getInfo());
                } else {
                    updatedTask.setInfo(task.getInfo());
                }

                try {
                    if (task.getType() == null || task.getType().isEmpty() || TaskTypeEnum.valueOf(task.getType()).equals(existsTask.get().getType())) {
                        updatedTask.setType(existsTask.get().getType());
                    } else {
                        updatedTask.setType(TaskTypeEnum.valueOf(task.getType()));
                    }
                } catch (Exception e) {
                    updatedTask.setType(TaskTypeEnum.UNKNOWN);
                }

                if (task.getProjectId() != null && task.getSubprojectId() == null) {
                    Optional<Project> project = projectRepository.findById(task.getProjectId());
                    if (project.isPresent()) {
                        updatedTask.setProject(project.get());
                        updatedTask.setSubproject(null);
                    } else {
                        return false;
                    }
                } else if (task.getProjectId() == null && task.getSubprojectId() != null) {
                    Optional<Subproject> subproject = subprojectRepository.findById(task.getSubprojectId());
                    if (subproject.isPresent()) {
                        updatedTask.setProject(null);
                        updatedTask.setSubproject(subproject.get());
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }

                updatedTask.setUsers(existsTask.get().getUsers());

                taskRepository.save(updatedTask);
                return true;
            }
        }
        return false;
    }

    private Users getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        Optional<Users> user = userRepository.findByUsername(securityUser.getUsername());
        return user.get();
    }

    private boolean isRoleOnlyUser(Users user) {
        String[] roles = user.getRoles().split(",");
        if (roles.length == 1) {
            return roles[0].equals("ROLE_USER");
        }
        return false;
    }
}
