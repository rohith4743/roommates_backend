package com.rohithkankipati.roommates.controller;

import com.rohithkankipati.roommates.dto.LoginRequestDTO;
import com.rohithkankipati.roommates.dto.UserDTO;
import com.rohithkankipati.roommates.service.UserService;
import com.rohithkankipati.roommates.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest) {
        try {
            UserDTO user = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
            String token = jwtTokenUtil.generateToken(user);
            user.setJwtToken(token);
            return ResponseEntity.ok().body(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/create")
    public ResponseEntity<UserDTO> createAccount(@RequestBody UserDTO userDto) {


        UserDTO createdUser = userService.createAccount(userDto);
        if (createdUser == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(createdUser);
    }

//    @PostMapping("/login")
//    public ResponseEntity<UserDTO> login(@RequestParam String username, @RequestParam String password) {
//        UserDTO user = userService.login(username, password);
//        if (user == null) {
//            return ResponseEntity.status(401).build();  // Unauthorized
//        }
//        return ResponseEntity.ok(user);
//    }

//    @PutMapping("/change-password")
//    public ResponseEntity<Void> changePassword(@RequestBody PasswordChangeDto passwordChangeDto) {
//        boolean success = userService.changePassword(
//            passwordChangeDto.getUserId(),
//            passwordChangeDto.getOldPassword(),
//            passwordChangeDto.getNewPassword()
//        );
//        if (!success) {
//            return ResponseEntity.badRequest().build();
//        }
//        return ResponseEntity.ok().build();
//    }
//
//    @PostMapping("/forget-password")
//    public ResponseEntity<Void> forgetPassword(@RequestParam String email) {
//        boolean success = userService.forgetPassword(email);
//        if (!success) {
//            return ResponseEntity.badRequest().build();
//        }
//        return ResponseEntity.ok().build();
//    }
//
//    @GetMapping("/profile/{userId}")
//    public ResponseEntity<UserDTO> getProfile(@PathVariable String userId) {
//        UserDTO user = userService.getProfile(userId);
//        if (user == null) {
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok(user);
//    }
//
//    @PostMapping("/logout")
//    public ResponseEntity<Void> logout(@RequestParam String userId) {
//        boolean success = userService.logout(userId);
//        if (!success) {
//            return ResponseEntity.badRequest().build();
//        }
//        return ResponseEntity.ok().build();
//    }
}
