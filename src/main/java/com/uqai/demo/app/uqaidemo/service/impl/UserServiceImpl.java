package com.uqai.demo.app.uqaidemo.service.impl;

import com.uqai.demo.app.uqaidemo.config.security.AuthCredential;
import com.uqai.demo.app.uqaidemo.config.security.TokenUtils;
import com.uqai.demo.app.uqaidemo.dto.ResponseError;
import com.uqai.demo.app.uqaidemo.dto.TokenDto;
import com.uqai.demo.app.uqaidemo.dto.user.UserDto;
import com.uqai.demo.app.uqaidemo.dto.user.UserMapper;
import com.uqai.demo.app.uqaidemo.enums.EnumStatus;
import com.uqai.demo.app.uqaidemo.enums.EnumTypeFile;
import com.uqai.demo.app.uqaidemo.model.car.Car;
import com.uqai.demo.app.uqaidemo.model.user.User;
import com.uqai.demo.app.uqaidemo.repository.UserRepository;
import com.uqai.demo.app.uqaidemo.service.UploadFileService;
import com.uqai.demo.app.uqaidemo.service.UserService;
import com.uqai.demo.app.uqaidemo.util.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private static final String USER_NOT_EXISTS = "El usuario no existe";
    private static final String USER_EXISTS = "El usuario o correo ya se encuentra registrado";
    @Autowired
    public UserRepository userRepository;
    @Autowired
    public UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UploadFileService uploadFileService;

    @Override
    public Page<UserDto> index(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findAll(pageable).map(userMapper::toDto);
    }

    @Override
    public ResponseEntity login(AuthCredential presenter) throws ResponseError {
        Optional<User> user = userRepository
                .findOneByEmailOrUsername(presenter.getUsername(), presenter.getUsername());

        if(user.isEmpty())
            throw new ResponseError("Credenciales incorrectas");

        if(user.get().getStatus().equals(EnumStatus.INA))
            throw new ResponseError("Usuario Bloqueado");

        if(passwordEncoder.matches(presenter.getPassword(), user.get().getPassword())){
            UserDto userDto = userMapper.toDto(user.get());
            return new ResponseEntity(new TokenDto(TokenUtils.generateToken(user.get().getUsername(), user.get().getEmail()), userDto), HttpStatus.OK);
        }else {
            throw new ResponseError("Credenciales incorrectas");
        }
    }

    @Override
    public UserDto showUser(Long userId) throws ResponseError {
        Optional<User> targetUser = userRepository.findById(userId);
        if(targetUser.isEmpty())
            throw new ResponseError(USER_NOT_EXISTS);

        return userMapper.toDto(targetUser.get());
    }

    @Override
    public UserDto createUser(UserDto userDto) throws ResponseError {
        Optional<User> user = userRepository.findOneByEmailOrUsername(userDto.getEmail(),userDto.getUsername());
        if(!user.isEmpty())
            throw new ResponseError(USER_EXISTS);

        userDto.setPassword(new BCryptPasswordEncoder().encode(userDto.getPassword()));
        return userMapper.toDto(userRepository.save(userMapper.toModel(userDto)));
    }

    @Override
    public UserDto updateUser(Long userId, UserDto userDto) throws ResponseError {
        Optional<User> targetUser = userRepository.findById(userId);
        if(targetUser.isEmpty())
            throw new ResponseError(USER_NOT_EXISTS);
        targetUser.get().setUsername(userDto.getUsername());
        targetUser.get().setEmail(userDto.getEmail());
        return userMapper.toDto(userRepository.save(targetUser.get()));
    }

    @Override
    public void deleteUser(Long userId) throws ResponseError {
        Optional<User> targetUser = userRepository.findById(userId);
        if(targetUser.isEmpty())
            throw new ResponseError(USER_NOT_EXISTS);
        targetUser.get().setStatus(EnumStatus.INA);
        userRepository.save(targetUser.get());
    }
    @Override
    public Long countUsers() {
        return userRepository.countByStatus(EnumStatus.ACT);
    }

    @Override
    public ResponseEntity<?> uploadImage(MultipartFile file, Long id, EnumTypeFile typeFile) {
        Optional<User> user = userRepository.findById(id);
        String fileNameDb = null;

        if (!user.isEmpty()) {
            String oldFileName = user.get().getAvatar();
            fileNameDb = uploadFileService.uploadImageCloud(file, typeFile.name());
            user.get().setAvatar(fileNameDb);
            if(oldFileName != null && !oldFileName.isBlank())
                uploadFileService.deleteImageOldCloud(oldFileName, typeFile.name());

            userRepository.save(user.get());
        }
        return new ResponseEntity<>(CustomResponse
                .builder()
                .type("OK")
                .message("La imagen fue correctamente cargada: ")
                .fieldUpdate(fileNameDb)
                .build(), HttpStatus.CREATED);
    }
}
