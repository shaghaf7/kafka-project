package com.backend.microservices.service;

import com.backend.microservices.model.User;
import com.backend.microservices.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.quota.ClientQuotaAlteration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User register(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> login(String username, String password) {

        Optional<User> user = userRepository.findByUsername(username);

        if (user.isPresent() &&
                passwordEncoder.matches(password, user.get().getPassword())) {
            return user;
        }

        return Optional.empty();
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public String deleteByUsername(String username) {

        Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty()) {
            return "User not found";
        }

        userRepository.deleteByUsername(username);
        return "User deleted successfully";
    }


}
