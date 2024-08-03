package org.example.taskmanager.services.ServiceImpl;

import jakarta.transaction.Transactional;
import org.example.taskmanager.DTO.RegistrationDto;
import org.example.taskmanager.models.Role;
import org.example.taskmanager.models.UserEntity;
import org.example.taskmanager.repositories.RoleRepository;
import org.example.taskmanager.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserDetailsService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public void saveUser(RegistrationDto registrationDto) {
        UserEntity user = new UserEntity();
        user.setUsername(registrationDto.getUsername());
        user.setEmail(registrationDto.getEmail());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        user.setRoles((Set<Role>) List.of(roleRepository.findByName("USER").get()));

        userRepository.save(user);
    }
    Optional<UserEntity> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(String.format("Пользователь %s не найден", email)));
        return userEntity;
    }

}
