package com.secretcow.holygrail.services.impl;

import com.secretcow.holygrail.dtos.user.RegisterUserDto;
import com.secretcow.holygrail.entities.UserDetail;
import com.secretcow.holygrail.exceptions.user.InvalidUserException;
import com.secretcow.holygrail.repositories.UserRepository;
import com.secretcow.holygrail.services.UserService;
import io.micrometer.common.util.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void createNewUser(RegisterUserDto registerUserDto) throws InvalidUserException {
        if(isValidUser(registerUserDto)) {
            UserDetail userToSave = createUserDetail(registerUserDto);
            userRepository.save(userToSave);
        }
    }

    private Boolean isValidUser(RegisterUserDto registerUserDto) throws InvalidUserException {
        if(registerUserDto == null) {
            throw new InvalidUserException("RegisterUserDto cannot be null");
        }
        if(StringUtils.isBlank(registerUserDto.getEmail())) {
            throw new InvalidUserException("Email cannot be empty or null.");
        }
        if(StringUtils.isBlank(registerUserDto.getUsername())){
            throw new InvalidUserException("Username cannot be empty or null");
        }
        if(StringUtils.isEmpty(registerUserDto.getPassword())) {
            throw new InvalidUserException("Password cannot be null or empty.");
        }
        return true;
    }

    private UserDetail createUserDetail(RegisterUserDto registerUserDto) {
        UserDetail createUserDetail = modelMapper.map(registerUserDto, UserDetail.class);
        createUserDetail.setPassword(passwordEncoder.encode(registerUserDto.getPassword()));
        createUserDetail.setActive(true);
        createUserDetail.setCreatedAt(LocalDateTime.now());
        createUserDetail.setUpdatedAt(LocalDateTime.now());
        return createUserDetail;
    }
}
