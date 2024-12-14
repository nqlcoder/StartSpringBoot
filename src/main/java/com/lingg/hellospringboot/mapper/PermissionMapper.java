package com.lingg.hellospringboot.mapper;

import org.mapstruct.Mapper;

import com.lingg.hellospringboot.dto.request.PermissionRequest;
import com.lingg.hellospringboot.dto.response.PermissionResponse;
import com.lingg.hellospringboot.entity.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);
}
