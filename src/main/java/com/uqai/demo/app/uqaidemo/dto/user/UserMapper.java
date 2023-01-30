package com.uqai.demo.app.uqaidemo.dto.user;

import com.uqai.demo.app.uqaidemo.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserMapper {
    @Mapping(target = "password", ignore = true)
    UserDto toDto(User model);

    User toModel(UserDto dto);
}
