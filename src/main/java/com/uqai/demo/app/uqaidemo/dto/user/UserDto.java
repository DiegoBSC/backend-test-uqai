package com.uqai.demo.app.uqaidemo.dto.user;

import com.uqai.demo.app.uqaidemo.enums.EnumStatus;
import com.uqai.demo.app.uqaidemo.model.user.Rol;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
public class UserDto {
    private Long id;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
    private EnumStatus status = EnumStatus.ACT;
    private String username;
    private String password;
    private String email;
    private String avatar;
    private Set<Rol> roles;
}
