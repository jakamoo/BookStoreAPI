package com.oredata.onlinebookstore.repository;

import com.oredata.onlinebookstore.model.dto.UserDTO;
import com.oredata.onlinebookstore.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

    UserEntity findByEmailAndPassword(String email,String password);
}
