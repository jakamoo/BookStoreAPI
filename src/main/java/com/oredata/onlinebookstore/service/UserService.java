package com.oredata.onlinebookstore.service;

import com.oredata.onlinebookstore.exception.CustomRestException;
import com.oredata.onlinebookstore.model.UserRole;
import com.oredata.onlinebookstore.model.dto.UserDTO;
import com.oredata.onlinebookstore.model.entity.UserEntity;
import com.oredata.onlinebookstore.repository.UserRepository;
import com.oredata.onlinebookstore.service.mapper.UserMapper;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Resource(name = "passwordEncoder")
    private final PasswordEncoder passwordEncoder;

    @Value("${custom.admin.email}")
    private String adminEmail;

    public UserDTO createUser(UserDTO userDto) {

        UserEntity userEntity = userMapper.dtoToEntity(userDto);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntity.setCreatedAt(LocalDate.now());
        setRole(userEntity);

        try {
            userRepository.save(userEntity);
            return userMapper.entityToDto(userEntity);

        } catch (DataIntegrityViolationException e) {


                throw new CustomRestException.UserAlreadyExistRestException(userDto.getEmail());


        }
    }

    public UserEntity findUserByEmailAndPassword(UserDTO user) {
        Optional<UserEntity> userByEmail = userRepository.findByEmail(user.getEmail());
        if (userByEmail.isPresent()) {
            if (passwordEncoder.matches(user.getPassword(), userByEmail.get().getPassword())) {
                return userByEmail.get();
            }
        }
        return null;
    }


    private void setRole(UserEntity user) {
        List<UserRole> roles = new LinkedList<>();

        if (user.getEmail().equals(adminEmail)) {

            roles=Arrays.stream(UserRole.values()).toList();

        } else {
            roles.add(UserRole.USER);
        }

        user.setRoles(roles);
    }



}