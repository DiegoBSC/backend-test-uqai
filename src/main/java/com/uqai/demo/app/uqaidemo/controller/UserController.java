package com.uqai.demo.app.uqaidemo.controller;

import com.uqai.demo.app.uqaidemo.dto.ResponseError;
import com.uqai.demo.app.uqaidemo.dto.user.UserDto;
import com.uqai.demo.app.uqaidemo.enums.EnumTypeFile;
import com.uqai.demo.app.uqaidemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1/user")
public class UserController {

    @Autowired
    public UserService userService;

    @GetMapping
    Page<UserDto> indexUsers(@RequestParam Integer page, @RequestParam Integer size){
        return userService.index(page, size);
    }
    @GetMapping("/{userId}")
    UserDto showUser(@PathVariable Long userId) throws ResponseError {
        return userService.showUser(userId);
    }
    @PostMapping
    UserDto createUser(@RequestBody UserDto userDto) throws ResponseError {
        return userService.createUser(userDto);
    }
    @PutMapping("/{userId}")
    UserDto updateUser(@PathVariable Long userId,
                       @RequestBody UserDto userDto) throws ResponseError {
        return userService.updateUser(userId,userDto);
    }
    @DeleteMapping("/{userId}")
    void deleteUser(@PathVariable Long userId) throws ResponseError {
        userService.deleteUser(userId);
    }
    @GetMapping("/users-total")
    public Long countTotalUsers(){
        return userService.countUsers();
    }

    @PostMapping("/upload-image")
    public ResponseEntity<?> uploadImage(@RequestPart("file") MultipartFile file, @RequestParam("userId") Long id,
                                         @RequestParam("typeFile") EnumTypeFile typeFile) {
        return userService.uploadImage(file,id,typeFile);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
