package com.nissum.createuser.services;

import com.nissum.createuser.dtos.request.UserDto;
import com.nissum.createuser.dtos.response.CreateUserResponse;
import com.nissum.createuser.exceptions.CreateUserHttpException;

public interface ICreateUserService {
    CreateUserResponse registerUser(UserDto userDto) throws CreateUserHttpException;
}
