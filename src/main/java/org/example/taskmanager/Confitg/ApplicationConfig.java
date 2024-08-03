package org.example.taskmanager.Confitg;

import lombok.RequiredArgsConstructor;
import org.example.taskmanager.models.UserEntity;
import org.example.taskmanager.repositories.UserRepository;
import org.example.taskmanager.services.ServiceImpl.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
    UserRepository userRepository;
    @Bean
    public UserDetailsService userDetailsService() {
        return email -> userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
    }
}
