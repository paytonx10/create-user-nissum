package com.nissum.createuser.controllers;

import com.nissum.createuser.dtos.request.UserDto;
import com.nissum.createuser.dtos.response.CreateUserResponse;
import com.nissum.createuser.exceptions.CreateUserHttpException;
import com.nissum.createuser.services.ICreateUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1")
@Tag(name = "Example API", description = "API de ejemplo para demostración")
public class CreateUserController {

    private final ICreateUserService createUserService;

    public CreateUserController(ICreateUserService createUserService) {
        this.createUserService = createUserService;
    }

    @PostMapping(path = "/register-user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreateUserResponse> registerUser(@RequestBody UserDto userDto)
            throws CreateUserHttpException {
        CreateUserResponse createUserResponse = createUserService.registerUser(userDto);
        return new ResponseEntity<>(createUserResponse, HttpStatus.OK);
    }
}