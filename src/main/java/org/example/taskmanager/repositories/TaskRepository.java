package org.example.taskmanager.repositories;

import org.example.taskmanager.DTO.MainDTO.TaskDTO;
import org.example.taskmanager.models.Task;
import org.example.taskmanager.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    TaskDTO findByTitle(String title);
    Task findById(long id);
    List<Task> findByAuthorEmail(String email);
    List<Task> findByAssignees(UserEntity assignee);
}
