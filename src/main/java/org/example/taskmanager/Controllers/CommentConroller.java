package org.example.taskmanager.Controllers;

import org.example.taskmanager.DTO.MainDTO.CommentDTO;
import org.example.taskmanager.models.UserEntity;
import org.example.taskmanager.services.ServiceImpl.CommentService;
import org.example.taskmanager.services.ServiceImpl.TaskService;
import org.example.taskmanager.services.ServiceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/task/comments")
public class CommentConroller {
    private CommentService commentService;
    private UserServiceImpl userService;

    @Autowired
    public CommentConroller(CommentService commentService,UserServiceImpl userService) {
        this.commentService = commentService;
        this.userService = userService;
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<List<CommentDTO>> getCommentsForTask(
            @PathVariable Long taskId) {
        List<CommentDTO> comments = commentService.getCommentsForTask(taskId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }
    @PostMapping("/createComment/{taskId}")
    public ResponseEntity<CommentDTO> createComment(
            @RequestBody CommentDTO commentDTO,@PathVariable Long taskId){
        UserEntity user = userService.getCurrentUser();
        CommentDTO createdComment = commentService.createComment(commentDTO, taskId,user.getEmail());
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }
}
