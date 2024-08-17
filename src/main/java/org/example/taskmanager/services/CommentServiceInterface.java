package org.example.taskmanager.services;

import org.example.taskmanager.DTO.MainDTO.CommentDTO;

public interface CommentServiceInterface {
    CommentDTO createComment(CommentDTO commentDTO, Long taskId, String authorEmail);
}
