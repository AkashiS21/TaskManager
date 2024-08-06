package org.example.taskmanager.repositories;

import org.example.taskmanager.DTO.MainDTO.TaskDTO;
import org.example.taskmanager.models.Task;
import org.example.taskmanager.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    TaskDTO findByTitle(String title);
    List<Task> findByAuthor(UserEntity author);
    List<Task> findByAssignee(UserEntity assignee);
}
