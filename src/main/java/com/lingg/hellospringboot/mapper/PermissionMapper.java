package com.lingg.hellospringboot.mapper;

import com.lingg.hellospringboot.dto.request.PermissionRequest;
import com.lingg.hellospringboot.dto.response.PermissionResponse;
import com.lingg.hellospringboot.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);
    PermissionResponse toPermissionResponse(Permission permission);
}
