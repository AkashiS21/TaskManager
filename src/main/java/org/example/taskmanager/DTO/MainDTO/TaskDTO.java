package org.example.taskmanager.DTO.MainDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.taskmanager.models.UserEntity;
import org.example.taskmanager.models.enums.TaskPriority;
import org.example.taskmanager.models.enums.TaskStatus;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {
    private String title;
    private String description;
    private TaskStatus status; // PENDING, IN_PROGRESS, COMPLETED
    private TaskPriority priority; // HIGH, MEDIUM, LOW
    private String authorEmail;
    private List<String> assignees;
}
