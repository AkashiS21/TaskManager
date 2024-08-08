package org.example.taskmanager.services.ServiceImpl;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.taskmanager.DTO.MainDTO.CommentDTO;
import org.example.taskmanager.DTO.MainDTO.TaskDTO;
import org.example.taskmanager.models.Comment;
import org.example.taskmanager.models.Task;
import org.example.taskmanager.models.UserEntity;
import org.example.taskmanager.repositories.CommentRepository;
import org.example.taskmanager.repositories.TaskRepository;
import org.example.taskmanager.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository,UserRepository userRepository, TaskRepository taskRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }
    public CommentDTO createComment(CommentDTO commentDTO, Long taskId, String authorEmail) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Задание не найдено"));
        UserEntity author = userRepository.findByEmail(authorEmail)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        Comment comment = Comment.builder()
                .task(task)
                .content(commentDTO.getContent())
                .createdAt(LocalDateTime.now())
                .authorMail(author.getEmail())
                .build();

        Comment savedComment = commentRepository.save(comment);
        return commentToDtoMapper(savedComment);
    }
    private CommentDTO commentToDtoMapper(Comment comment) {
        CommentDTO dto = new CommentDTO();
        dto.setTaskId(comment.getTask().getId());
        dto.setContent(comment.getContent());
        dto.setCreatedAt(comment.getCreatedAt());
        dto.setAuthorEmail(comment.getAuthorMail());
        return dto;
    }
    public List<CommentDTO> getCommentsForTask(Long taskId) {
        Task task = taskRepository.findById(taskId).get();
        List<Comment> comments = task.getComments();
        return comments.stream().map(this::commentToDtoMapper).toList();
    }
}
