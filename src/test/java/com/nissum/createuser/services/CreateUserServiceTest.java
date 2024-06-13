package com.nissum.createuser.services;

import com.nissum.createuser.dtos.request.PhoneDto;
import com.nissum.createuser.dtos.request.UserDto;
import com.nissum.createuser.dtos.response.CreateUserResponse;
import com.nissum.createuser.exceptions.CreateUserHttpException;
import com.nissum.createuser.mappers.CreateUserMapper;
import com.nissum.createuser.repository.UserRepository;
import com.nissum.createuser.repository.model.Phone;
import com.nissum.createuser.repository.model.User;
import com.nissum.createuser.utils.EmailValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateUserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CreateUserMapper createUserMapper;

    @Mock
    private EmailValidator emailValidator;

    @InjectMocks
    private CreateUserService createUserService;

    private UserDto userDto;
    private User user;

    @BeforeEach
    public void setUp() {
        PhoneDto phoneDto = PhoneDto.builder()
                .number("12345")
                .build();

        List<PhoneDto> phoneDtos = new ArrayList<>();
        phoneDtos.add(phoneDto);

        userDto = UserDto.builder()
                .email("test@example.com")
                .name("Test User")
                .password("password")
                .phones(phoneDtos)
                .build();

        Phone phone = Phone.builder()
                .number("12345")
                .build();
        List<Phone> phones = new ArrayList<>();
        phones.add(phone);

        user = User.builder()
                .email("test@example.com")
                .name("Test User")
                .password("password")
                .created(new Timestamp(System.currentTimeMillis()))
                .modified(new Timestamp(System.currentTimeMillis()))
                .lastLogin(new Timestamp(System.currentTimeMillis()))
                .phones(phones)
                .build();
    }

    @Test
    public void testRegisterUser_InvalidEmail() {
        userDto = UserDto.builder()
                .email("test..example.com")
                .build();
        CreateUserHttpException exception = assertThrows(CreateUserHttpException.class, () -> {
            createUserService.registerUser(userDto);
        });
        assertEquals("Invalid email format", exception.getMessage());
    }

    @Test
    public void testRegisterUser_EmailAlreadyExists() {
        when(userRepository.existsByEmail(userDto.getEmail())).thenReturn(true);
        CreateUserHttpException exception = assertThrows(CreateUserHttpException.class, () -> {
            createUserService.registerUser(userDto);
        });
        assertEquals("Email already exists", exception.getMessage());
    }

    @Test
    public void testRegisterUser_Exception() {
        when(userRepository.existsByEmail(userDto.getEmail())).thenReturn(false);
        CreateUserHttpException exception = assertThrows(CreateUserHttpException.class, () -> {
            createUserService.registerUser(userDto);
        });
        assertEquals("User saved is null", exception.getMessage());
    }

    @Test
    public void testRegisterUser_Success() throws CreateUserHttpException {
        when(userRepository.existsByEmail(userDto.getEmail())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);

        CreateUserResponse response = createUserService.registerUser(userDto);

        assertNotNull(response);
        verify(userRepository, times(1)).save(any(User.class));
    }
}
