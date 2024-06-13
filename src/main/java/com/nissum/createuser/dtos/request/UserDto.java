package com.nissum.createuser.dtos.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
public class UserDto {
    private String name;
    private String email;
    private String password;
    private List<PhoneDto> phones;
    private UUID id;
    private String created;
    private String modified;
    private String lastLogin;
    private UUID token;
    private Boolean isActive;
}
