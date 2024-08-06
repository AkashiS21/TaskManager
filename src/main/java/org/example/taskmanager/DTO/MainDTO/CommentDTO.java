package org.example.taskmanager.DTO.MainDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    private Long id;
    private String text;
    private LocalDateTime createdAt;
    private Long taskId;
    private Long userId;
}
