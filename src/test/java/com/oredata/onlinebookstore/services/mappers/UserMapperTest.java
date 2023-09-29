package com.oredata.onlinebookstore.services.mappers;


import com.oredata.onlinebookstore.model.UserRole;
import com.oredata.onlinebookstore.model.dto.UserDTO;
import com.oredata.onlinebookstore.model.entity.UserEntity;
import com.oredata.onlinebookstore.service.mapper.UserMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;




@SpringBootTest
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    UserEntity userEntity = new UserEntity("UserName", "user@gmail.com", "userpassword",List.of(UserRole.USER));

    @Test
    public void testUserMapperReturnsUserDTOOfGivenUserEntity() {
        UserDTO userDTO = userMapper.entityToDto(userEntity);
        Assertions.assertEquals(userEntity.getName(), userDTO.getName());
        Assertions.assertEquals(userEntity.getEmail(), userDTO.getEmail());
        Assertions.assertEquals(userEntity.getPassword(), userDTO.getPassword());
    }




}
