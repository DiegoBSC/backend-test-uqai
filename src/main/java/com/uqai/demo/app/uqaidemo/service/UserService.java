package com.uqai.demo.app.uqaidemo.service;

import com.uqai.demo.app.uqaidemo.config.security.AuthCredential;
import com.uqai.demo.app.uqaidemo.dto.ResponseError;
import com.uqai.demo.app.uqaidemo.dto.user.UserDto;
import com.uqai.demo.app.uqaidemo.enums.EnumTypeFile;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    Page<UserDto> index(Integer Page, Integer size);
    ResponseEntity login(AuthCredential presenter) throws ResponseError;
    UserDto showUser(Long userId) throws ResponseError;
    UserDto createUser(UserDto userDto) throws ResponseError;
    UserDto updateUser(Long userId, UserDto userDto) throws ResponseError;
    void deleteUser(Long userId) throws ResponseError;
    Long countUsers();
    ResponseEntity<?> uploadImage(MultipartFile file, Long id, EnumTypeFile typeFile);
}
