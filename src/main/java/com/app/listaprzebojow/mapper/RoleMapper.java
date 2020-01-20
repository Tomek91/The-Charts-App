package com.app.listaprzebojow.mapper;

import com.app.listaprzebojow.dto.RoleDTO;
import com.app.listaprzebojow.model.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleDTO roleToRoleDTO(Role role);
    Role roleDTOToRole(RoleDTO roleDTO);
}
