package com.smartplanner.service.implementation;

import com.smartplanner.model.User;
import com.smartplanner.repository.UserRepository;
import com.smartplanner.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean findUserById(int id) {
        return userRepository.existsById(id);
    }

    public User getUserById(int id) {
        return userRepository.getOne(id);
    }
}
