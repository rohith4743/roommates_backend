package com.rohithkankipati.roommates.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.rohithkankipati.roommates.dto.UserDTO;
import com.rohithkankipati.roommates.entity.UserEntity;
import com.rohithkankipati.roommates.exception.RoomMateException;
import com.rohithkankipati.roommates.model.UserRole;
import com.rohithkankipati.roommates.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private UserDTO userDTO;
    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
	userDTO = new UserDTO();
	userDTO.setId("1");
	userDTO.setUserName("testuser");
	userDTO.setEmail("testuser@example.com");
	userDTO.setFirstName("Test");
	userDTO.setLastName("User");
	userDTO.setPassword("password");
	userDTO.setRoles(Collections.singleton(UserRole.USER));
	userDTO.setMobileNumber("1234567890");

	userEntity = new UserEntity(userDTO);
	userEntity.setPassword("encodedPassword");
    }

    @Test
    void createAccount_success() {
	when(userRepository.existsByUserName(userDTO.getUserName())).thenReturn(false);
	when(userRepository.existsByEmail(userDTO.getEmail())).thenReturn(false);
	when(userRepository.existsByMobileNumber(userDTO.getMobileNumber())).thenReturn(false);
	when(passwordEncoder.encode(userDTO.getPassword())).thenReturn("encodedPassword");
	when(userRepository.save(any(UserEntity.class))).thenReturn(new UserEntity(userDTO));

	UserDTO result = userService.createAccount(userDTO);

	assertThat(result).isNotNull();
	assertThat(result.getUserName()).isEqualTo("testuser");
	verify(userRepository).save(any(UserEntity.class));
    }

    @Test
    void createAccount_usernameExists() {
	when(userRepository.existsByUserName(userDTO.getUserName())).thenReturn(true);

	RoomMateException exception = assertThrows(RoomMateException.class, () -> userService.createAccount(userDTO));

	assertThat(exception.getMessage()).isEqualTo("Username is already in use.");
	assertThat(exception.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void createAccount_emailExists() {
	when(userRepository.existsByUserName(userDTO.getUserName())).thenReturn(false);
	when(userRepository.existsByEmail(userDTO.getEmail())).thenReturn(true);

	RoomMateException exception = assertThrows(RoomMateException.class, () -> userService.createAccount(userDTO));

	assertThat(exception.getMessage()).isEqualTo("Email is already registered.");
	assertThat(exception.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void createAccount_mobileExists() {
	when(userRepository.existsByUserName(userDTO.getUserName())).thenReturn(false);
	when(userRepository.existsByEmail(userDTO.getEmail())).thenReturn(false);
	when(userRepository.existsByMobileNumber(userDTO.getMobileNumber())).thenReturn(true);

	RoomMateException exception = assertThrows(RoomMateException.class, () -> userService.createAccount(userDTO));

	assertThat(exception.getMessage()).isEqualTo("Mobile number is already associated with an account.");
	assertThat(exception.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void login_success() {
	when(userRepository.findByUsernameOrEmailOrPhoneNumber("testuser")).thenReturn(Optional.of(userEntity));
	when(passwordEncoder.matches("password", "encodedPassword")).thenReturn(true);

	UserDTO result = userService.login("testuser", "password");

	assertThat(result).isNotNull();
	assertThat(result.getUserName()).isEqualTo("testuser");
    }

    @Test
    void login_invalidCredentials() {
	when(userRepository.findByUsernameOrEmailOrPhoneNumber("testuser")).thenReturn(Optional.of(userEntity));
	when(passwordEncoder.matches("wrongpassword", "encodedPassword")).thenReturn(false);

	RoomMateException exception = assertThrows(RoomMateException.class,
		() -> userService.login("testuser", "wrongpassword"));

	assertThat(exception.getMessage()).isEqualTo("The Email or Password doesn't match.");
	assertThat(exception.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void login_userNotFound() {
	when(userRepository.findByUsernameOrEmailOrPhoneNumber("unknownuser")).thenReturn(Optional.empty());

	RoomMateException exception = assertThrows(RoomMateException.class,
		() -> userService.login("unknownuser", "password"));

	assertThat(exception.getMessage()).isEqualTo("The Email or Password doesn't match.");
	assertThat(exception.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
