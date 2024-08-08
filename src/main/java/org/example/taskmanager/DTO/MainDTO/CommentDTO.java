package org.example.taskmanager.DTO.MainDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    private String content;
    private LocalDateTime createdAt;
    private String authorEmail;
    private Long taskId;
}
