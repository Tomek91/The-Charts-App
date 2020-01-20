package com.app.listaprzebojow.mapper;

import com.app.listaprzebojow.dto.UserDTO;
import com.app.listaprzebojow.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mappings({
            @Mapping(source = "role", target = "roleDTO")
    })
    UserDTO userToUserDTO(User user);

    @Mappings({
            @Mapping(source = "roleDTO", target = "role")
    })
    User userDTOToUser(UserDTO userDTO);
}

