package com.nissum.createuser.services;

import com.nissum.createuser.constants.FieldConstant;
import com.nissum.createuser.dtos.request.UserDto;
import com.nissum.createuser.dtos.response.CreateUserResponse;
import com.nissum.createuser.exceptions.CreateUserHttpException;
import com.nissum.createuser.mappers.CreateUserMapper;
import com.nissum.createuser.repository.UserRepository;
import com.nissum.createuser.repository.model.User;
import com.nissum.createuser.utils.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateUserService implements ICreateUserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public CreateUserResponse registerUser(UserDto userDto) throws CreateUserHttpException {
        CreateUserResponse response;
        try {
            if (!EmailValidator.isValidEmail(userDto.getEmail())) {
                throw new CreateUserHttpException(FieldConstant.SAVE_ERROR,"Invalid email format");
            }
            if (userRepository.existsByEmail(userDto.getEmail())) {
                throw new CreateUserHttpException(FieldConstant.SAVE_ERROR,"Email already exists");
            }
            User userSet = CreateUserMapper.buildSave(userDto);
            User userSaved = userRepository.save(userSet);
            response = CreateUserMapper.buildResponse(userDto, userSaved);
        }catch(Exception e){
            throw new CreateUserHttpException(FieldConstant.SAVE_ERROR,e.getMessage());
        }
        return response;
    }
}
