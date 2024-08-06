package org.example.taskmanager.services.ServiceImpl;

import lombok.NoArgsConstructor;
import org.example.taskmanager.DTO.Auth.AuthenicationResponse;
import org.example.taskmanager.DTO.Auth.AuthenticationRequest;
import org.example.taskmanager.DTO.Auth.RegisterationRequest;
import org.example.taskmanager.models.enums.Role;
import org.example.taskmanager.models.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor

public class AuthenticationService {
    private JwtService jwtService;
    private PasswordEncoder passwordEncoder;
    private UserServiceImpl userService;
    private AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationService(JwtService jwtService, PasswordEncoder passwordEncoder, UserServiceImpl userService, AuthenticationManager authenticationManager) {
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }
    public AuthenicationResponse register(RegisterationRequest request) {

        UserEntity user = UserEntity.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_USER)
                .build();
        userService.createUser(user);

        var jwtToken = jwtService.generateToken(user);
        return AuthenicationResponse.builder().token(jwtToken).build();
    }

    public AuthenicationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword())
        );
        var user = userService.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenicationResponse.builder().token(jwtToken).build();
    }
}
