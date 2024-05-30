package com.rohithkankipati.roommates.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rohithkankipati.roommates.dto.LoginRequestDTO;
import com.rohithkankipati.roommates.dto.UserDTO;
import com.rohithkankipati.roommates.exception.RoomMateException;
import com.rohithkankipati.roommates.service.UserService;
import com.rohithkankipati.roommates.util.JwtTokenUtil;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/api/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest) {
	try {
	    UserDTO user = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
	    String token = jwtTokenUtil.generateToken(user);
	    user.setJwtToken(token);
	    return ResponseEntity.ok().body(user);
	} catch (RoomMateException e) {
	    throw e;
	}
    }

    @PostMapping("/api/create")
    public ResponseEntity<UserDTO> createAccount(@RequestBody UserDTO userDto) {
	try {

	    UserDTO createdUser = userService.createAccount(userDto);
	    String token = jwtTokenUtil.generateToken(createdUser);
	    createdUser.setJwtToken(token);
	    return ResponseEntity.ok(createdUser);
	} catch (RoomMateException e) {
	    throw e;
	}
    }

    @GetMapping("/profile/{userName}")
    public ResponseEntity<UserDTO> getProfile(@PathVariable String userName) {

	try {
	    UserDTO createdUser = userService.getProfile(userName);
	    return ResponseEntity.ok(createdUser);
	} catch (RoomMateException e) {
	    throw e;
	}

    }

    @GetMapping("/api/username-exists")
    public ResponseEntity<?> checkUsernameExists(@RequestParam String username) {
	boolean exists = userService.checkUsernameExists(username);
	return ResponseEntity.ok().body("{\"exists\": " + exists + "}");
    }

    @PostMapping("/change-password")
    @ResponseStatus(value = HttpStatus.NOT_IMPLEMENTED, reason = "This API feature is not implemented yet.")
    public ResponseEntity<Void> changePassword(@RequestBody Map<String, Object> passwordChangeDto) {
	return null;

    }

    @PostMapping("/forget-password")
    @ResponseStatus(value = HttpStatus.NOT_IMPLEMENTED, reason = "This API feature is not implemented yet.")
    public ResponseEntity<Void> forgetPassword(@RequestParam String email) {
	return null;
    }

    @PostMapping("/logout")
    @ResponseStatus(value = HttpStatus.NOT_IMPLEMENTED, reason = "This API feature is not implemented yet.")
    public ResponseEntity<Void> logout(@RequestParam String userId) {
	return null;
    }
}
