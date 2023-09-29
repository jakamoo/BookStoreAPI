package com.oredata.onlinebookstore.model.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class UserDTO {


    private String name;

    private String email;

    private String password;
}
