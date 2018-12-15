package com.smartplanner.service;

import com.smartplanner.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    boolean findUserById(int id);

    User getUserById(int id);
}
