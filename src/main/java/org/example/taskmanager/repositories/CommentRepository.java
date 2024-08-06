package org.example.taskmanager.repositories;

import org.example.taskmanager.models.Comment;
import org.example.taskmanager.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByTask(Task task);
}
