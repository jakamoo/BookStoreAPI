package com.oredata.onlinebookstore.controller;

import com.oredata.onlinebookstore.model.dto.UserDTO;
import com.oredata.onlinebookstore.model.entity.UserEntity;
import com.oredata.onlinebookstore.service.JwtUtils;
import com.oredata.onlinebookstore.service.UserService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    @Resource(name = "jwtUtils")
    private JwtUtils jwtUtils;


    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO userInput) {

            UserDTO userDTO = userService.createUser(userInput);
            return new ResponseEntity<>(userDTO, HttpStatus.CREATED);

    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserDTO user) {
        if (user.getEmail() == null || user.getPassword() == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        UserEntity userData = userService.findUserByEmailAndPassword(user);

        if (userData == null) {
            return new ResponseEntity<>(false, HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(jwtUtils.generateToken(user), HttpStatus.OK);
    }


}

