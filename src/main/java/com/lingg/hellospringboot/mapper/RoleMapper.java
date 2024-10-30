package com.lingg.hellospringboot.mapper;

import com.lingg.hellospringboot.dto.request.RoleRequest;
import com.lingg.hellospringboot.dto.response.RoleResponse;
import com.lingg.hellospringboot.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);
    RoleResponse toRoleResponse(Role role);
}
