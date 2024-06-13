package com.nissum.createuser.mappers;

import com.nissum.createuser.constants.FieldConstant;
import com.nissum.createuser.dtos.request.PhoneDto;
import com.nissum.createuser.dtos.request.UserDto;
import com.nissum.createuser.dtos.response.CreateUserResponse;
import com.nissum.createuser.exceptions.CreateUserHttpException;
import com.nissum.createuser.repository.model.Phone;
import com.nissum.createuser.repository.model.User;
import com.nissum.createuser.utils.Util;
import org.springframework.util.ObjectUtils;

import java.util.List;

public class CreateUserMapper {

    private CreateUserMapper(){}

    public static User buildSave(UserDto userRequest){
        return User.builder()
                .name(userRequest.getName())
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
                .phones(getPhones(userRequest.getPhones()))
                .build();
    }

    private static List<Phone> getPhones(List<PhoneDto> phoneDtos) {
        return phoneDtos.stream().map(phoneDto ->
                Phone.builder()
                        .number(phoneDto.getNumber())
                        .cityCode(phoneDto.getCityCode())
                        .countryCode(phoneDto.getCountryCode())
                        .build()
        ).toList();
    }

    public static CreateUserResponse buildResponse(UserDto userRequest, User user) throws CreateUserHttpException {
        if(!ObjectUtils.isEmpty(user)){
            userRequest.setId(user.getId());
            userRequest.setCreated(Util.convertTimestampToString(user.getCreated()));
            userRequest.setModified(Util.convertTimestampToString(user.getModified()));
            userRequest.setLastLogin(Util.convertTimestampToString(user.getLastLogin()));
            userRequest.setToken(user.getToken());
            userRequest.setIsActive(user.getIsActive());
            return CreateUserResponse.builder()
                    .message(FieldConstant.SAVE_SUCCESS)
                    .description(userRequest)
                    .build();
        }
        throw new CreateUserHttpException(FieldConstant.SAVE_ERROR, "User saved is null");
    }
}
