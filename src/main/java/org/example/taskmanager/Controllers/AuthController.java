package org.example.taskmanager.Controllers;

import lombok.RequiredArgsConstructor;
import org.example.taskmanager.DTO.Auth.AuthenicationResponse;
import org.example.taskmanager.DTO.Auth.AuthenticationRequest;
import org.example.taskmanager.DTO.Auth.RegisterationRequest;
import org.example.taskmanager.services.ServiceImpl.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/register")
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping()
    public ResponseEntity<AuthenicationResponse> register(@RequestBody RegisterationRequest request ) {
        return ResponseEntity.ok(authenticationService.register(request));
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenicationResponse> register(@RequestBody AuthenticationRequest request ) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
