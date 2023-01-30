package com.uqai.demo.app.uqaidemo.dto;

import com.uqai.demo.app.uqaidemo.dto.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenDto {

    private String accessToken;
    private UserDto user;
}
