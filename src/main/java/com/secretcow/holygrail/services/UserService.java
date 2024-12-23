package com.secretcow.holygrail.services;

import com.secretcow.holygrail.dtos.user.RegisterUserDto;
import com.secretcow.holygrail.exceptions.user.InvalidUserException;

public interface UserService {
    void createNewUser(RegisterUserDto registerUserDto) throws InvalidUserException;
}
