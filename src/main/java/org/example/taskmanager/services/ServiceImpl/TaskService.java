package org.example.taskmanager.services.ServiceImpl;

import lombok.NoArgsConstructor;
import org.example.taskmanager.DTO.MainDTO.CommentDTO;
import org.example.taskmanager.DTO.MainDTO.TaskDTO;
import org.example.taskmanager.models.Comment;
import org.example.taskmanager.models.Task;
import org.example.taskmanager.models.UserEntity;
import org.example.taskmanager.models.enums.TaskStatus;
import org.example.taskmanager.repositories.TaskRepository;
import org.example.taskmanager.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
                .orElseThrow(() -> new RuntimeException("Задание не найдено"));
    }
    public Task createTask(TaskDTO taskDTO , UserEntity userEntity) {
        userEntity = userRepository.findById(userEntity.getId()).orElseThrow(() -> new RuntimeException("Пользователя не существет"));
        Task task = Task.builder()
                .title(taskDTO.getTitle())
                .description(taskDTO.getDescription())
                .priority(taskDTO.getPriority())
                .status(taskDTO.getStatus())
                .authorEmail(userEntity.getEmail())
                .build();
        if (taskDTO.getAssignees() != null && !taskDTO.getAssignees().isEmpty()) {
            List<UserEntity> assignes = userRepository.findByEmailIn(taskDTO.getAssignees());
            task.setAssignees(assignes);
        }
        return taskRepository.save(task);
    }
    public Task updateTask(Long taskId, TaskDTO taskDTO,UserEntity user) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Задание не найдено"));

        if (!task.getAuthorEmail().equals(user.getEmail())) {
            throw new SecurityException("Вы не можете изменять эту задачу");
        }

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
    public void setTaskAssignee(Long taskId, List<String> assigneesMail) {
        List<UserEntity> assignees = assigneesMail.stream()
                .map(f -> (UserEntity) userRepository.findByEmailIn(Collections.singletonList(f)))
                .toList();
        Task task = getTaskById(taskId);
        task.setAssignees(assignees);
        taskRepository.save(task);
    }
    public List<TaskDTO> getTasksByAuthorEmail(String userEmail) {
        List<Task> tasks = taskRepository.findByAuthorEmail(userEmail);
        return tasks.stream().map(TaskService::taskToDtoMapper).toList();
    }
    public List<TaskDTO> getTasksByAsseneeEmail(String assegneeEmail){
        Optional<UserEntity> assegne = userRepository.findByEmail(assegneeEmail);
        List<Task> tasks = taskRepository.findByAssignees(assegne.get());
        return tasks.stream().map(TaskService::taskToDtoMapper).toList();
    }
    public void deleteTask(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Задание не найдено"));

        taskRepository.delete(task);
    }
    public List<TaskDTO> getAllMyTasks(UserEntity user) {
        List<Task> tasksByAuthor = taskRepository.findByAuthorEmail(user.getEmail());
        List<Task> tasksByAssignee = taskRepository.findByAssignees(user);

        Set<Task> allTasks = new HashSet<>();
        allTasks.addAll(tasksByAuthor);
        allTasks.addAll(tasksByAssignee);

        return allTasks.stream()
                .map(TaskService::taskToDtoMapper)
                .collect(Collectors.toList());
    }


    /*public List<Task> getTasksForUserEmail(String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        return taskRepository.findByAuthorEmail(email);
    }*/
    public Task updateTaskStatus(Long taskId, TaskStatus taskStatus) {
        Task task = getTaskById(taskId);
        if (!isValidStatus(taskStatus)) {
            throw new IllegalArgumentException("Неверный статус задачи");
        }
        task.setStatus(taskStatus);
        return taskRepository.save(task);
    }
    private boolean isValidStatus(TaskStatus status) {
        return Arrays.asList("PENDING", "IN_PROGRESS", "COMPLETED").contains(status);
    }
    public static TaskDTO taskToDtoMapper(Task task) {
        TaskDTO dto = new TaskDTO();
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setStatus(task.getStatus());
        dto.setPriority(task.getPriority());
        dto.setAuthorEmail(task.getAuthorEmail());
        dto.setAssignees(task.getAssignees().stream().map(UserEntity::getEmail).collect(Collectors.toList()));
        return dto;
    }
}
