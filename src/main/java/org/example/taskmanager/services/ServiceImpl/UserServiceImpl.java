package org.example.taskmanager.services.ServiceImpl;

import jakarta.transaction.Transactional;
import org.example.taskmanager.models.UserEntity;
import org.example.taskmanager.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserDetailsService {
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public UserEntity saveUser(UserEntity user) {
        return userRepository.save(user);
    }
    public UserEntity createUser(UserEntity user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UsernameNotFoundException("Пользователь с таким именем уже существует");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UsernameNotFoundException("Пользователь с таким email уже существует");
        }
        return saveUser(user);
        /*UserEntity user = new UserEntity();
        user.setUsername(registerationRequest.getUsername());
        user.setEmail(registerationRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerationRequest.getPassword()));
        user.setRoles((Set<Role>) List.of(roleRepository.findByRole("USER")));

        userRepository.save(user);
        return user;*/
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
