package com.lingg.hellospringboot.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.lingg.hellospringboot.dto.request.RoleRequest;
import com.lingg.hellospringboot.dto.response.RoleResponse;
import com.lingg.hellospringboot.entity.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}
