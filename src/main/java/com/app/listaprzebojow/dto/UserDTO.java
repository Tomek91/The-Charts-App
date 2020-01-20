package com.app.listaprzebojow.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private String name;
    private String surname;
    private String username;
    private String password;
    private String passwordConfirmation;
    private RoleDTO roleDTO;
}
