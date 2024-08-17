package org.example.taskmanager.services;

import org.example.taskmanager.DTO.Auth.AuthenicationResponse;
import org.example.taskmanager.DTO.Auth.AuthenticationRequest;
import org.example.taskmanager.DTO.Auth.RegisterationRequest;

public interface AuthenticationServiceInterface {
    AuthenicationResponse register(RegisterationRequest request);
    AuthenicationResponse authenticate(AuthenticationRequest request);
}
