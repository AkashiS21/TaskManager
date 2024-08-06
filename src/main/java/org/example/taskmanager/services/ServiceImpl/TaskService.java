package org.example.taskmanager.services.ServiceImpl;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.example.taskmanager.DTO.MainDTO.TaskDTO;
import org.example.taskmanager.models.Task;
import org.example.taskmanager.models.UserEntity;
import org.example.taskmanager.repositories.TaskRepository;
import org.example.taskmanager.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@NoArgsConstructor
public class TaskService {
    private TaskRepository taskRepository;
    private UserRepository userRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }
    public Task getTaskById(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
    }
    public Task createTask(TaskDTO taskDTO , UserEntity userEntity) {
        userEntity = userRepository.findById(userEntity.getId()).orElseThrow(() -> new RuntimeException("Пользователя не существет"));
        Task task = Task.builder()
                .title(taskDTO.getTitle())
                .description(taskDTO.getDescription())
                .priority(taskDTO.getPriority())
                .status(taskDTO.getStatus())
                .author(userEntity)
                .build();
        if (taskDTO.getAssignees() != null && !taskDTO.getAssignees().isEmpty()) {
            List<UserEntity> assignes = userRepository.findByEmailIn(taskDTO.getAssignees());
            task.setAssignees(assignes);
        }
        return taskRepository.save(task);
    }
    public Task updateTask(Long taskId, TaskDTO taskDTO) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Задание не найдено"));

        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setPriority(taskDTO.getPriority());
        task.setStatus(taskDTO.getStatus());

        if (taskDTO.getAssignees() != null && !taskDTO.getAssignees().isEmpty()) {
            List<UserEntity> assignees = userRepository.findByEmailIn(taskDTO.getAssignees());
            task.setAssignees(assignees);
        }

        return taskRepository.save(task);
    }
    public void deleteTask(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Задание не найдено"));

        taskRepository.delete(task);
    }

}
