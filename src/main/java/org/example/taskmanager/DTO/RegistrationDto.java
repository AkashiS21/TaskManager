package org.example.taskmanager.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistrationDto {
    private Long userId;
    private String username;
    private String email;
    private String password;
    private String repeatedPassword;
}
