package com.smartplanner.controller;

import com.smartplanner.exception.ResourceNotFoundException;
import com.smartplanner.model.User;
import com.smartplanner.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("{id}")
    public User getUserById(@PathVariable(value = "id") int id) throws ResourceNotFoundException {
        if (!userService.findUserById(id)) {
            throw new ResourceNotFoundException("User", "id", id);
        }

        return userService.getUserById(id);
    }
}
