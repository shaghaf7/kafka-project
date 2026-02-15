package com.backend.microservices.service;

import com.backend.microservices.model.User;
import com.backend.microservices.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.quota.ClientQuotaAlteration;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public User register(User user){
        return userRepository.save(user);
    }

    public Optional<User> login (String username, String password){
        Optional<User> user=userRepository.findByUsername(username);
        if(user.isPresent()&&user.get().getPassword().equals(password)){
            return user;
        }
        return Optional.empty();
    }

}
