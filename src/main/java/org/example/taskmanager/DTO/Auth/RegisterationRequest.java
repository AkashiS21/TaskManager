package org.example.taskmanager.DTO.Auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterationRequest {
    private String email;
    private String username;
    private String password;
}
