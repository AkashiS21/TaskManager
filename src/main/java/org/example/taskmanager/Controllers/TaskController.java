package org.example.taskmanager.Controllers;

import org.example.taskmanager.DTO.MainDTO.TaskDTO;
import org.example.taskmanager.models.Task;
import org.example.taskmanager.models.UserEntity;
import org.example.taskmanager.models.enums.TaskStatus;
import org.example.taskmanager.services.ServiceImpl.TaskService;
import org.example.taskmanager.services.ServiceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
public class TaskController {
    private TaskService taskService;
    private UserServiceImpl userService;

    @Autowired
    public TaskController(TaskService taskService, UserServiceImpl userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<List<TaskDTO>> getAllMyTask() {
        return new ResponseEntity<>(taskService.getAllMyTasks(userService.getCurrentUser())
                ,HttpStatus.OK);
    }
    @PostMapping("/createNewTask")
    public ResponseEntity<Task> createTask(@RequestBody TaskDTO taskDTO) {
        return new ResponseEntity<>(taskService.createTask(taskDTO,userService.getCurrentUser())
                ,HttpStatus.OK);
    }
    @PutMapping("/updateTask/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody TaskDTO taskDTO) {
        UserEntity user = userService.getCurrentUser();
        return new ResponseEntity<>(taskService.updateTask(id,taskDTO,user)
                ,HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PatchMapping("/{id}/updateStatus")
    public ResponseEntity<TaskDTO> updateTaskStatus(@PathVariable Long id, @RequestParam TaskStatus taskStatus) {
        Task task = taskService.updateTaskStatus(id,taskStatus);
        return new ResponseEntity<>(TaskService.taskToDtoMapper(task), HttpStatus.OK);
    }
    @GetMapping("/tasksByAuthor/{authorEmail}")
    public ResponseEntity<List<TaskDTO>> getTasksByAuthor(@PathVariable String authorEmail) {
        List<TaskDTO> tasks = taskService.getTasksByAuthorEmail(authorEmail);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }
    @GetMapping("/taskByAssegnee/{assegneeEmail}")
    public ResponseEntity<List<TaskDTO>> getTaskByAssignee(@PathVariable String assegneeEmail) {
        List<TaskDTO> tasks = taskService.getTasksByAsseneeEmail(assegneeEmail);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }
}
