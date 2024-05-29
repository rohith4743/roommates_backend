package com.rohithkankipati.roommates.dto;

import java.util.Set;
import com.rohithkankipati.roommates.entity.UserEntity;
import com.rohithkankipati.roommates.model.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserDTO {
  
  private String id;

  @NotBlank(message = "{email.required}")
  @Email(message = "{email.invalid}")
  private String email;

  @NotBlank(message = "{username.required}")
  @Size(min = 3, max = 20, message = "{username.size}")
  private String userName;

  @NotBlank(message = "{firstname.required}")
  private String firstName;

  @NotBlank(message = "{lastname.required}")
  private String lastName;

  @NotBlank(message = "{password.required}")
  @Size(min = 8, message = "{password.size}")
  @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "{password.pattern}")
  private String password;
  
  @NotBlank(message = "{password.required}")
  @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "{mobilenumber.invalid}")
  private String mobileNumber;
  
  private String jwtToken;
  
  private Set<UserRole> roles;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Set<UserRole> getRoles() {
    return roles;
  }

  public void setRoles(Set<UserRole> roles) {
    this.roles = roles;
  }

  public String getMobileNumber() {
    return mobileNumber;
  }

  public void setMobileNumber(String mobileNumber) {
    this.mobileNumber = mobileNumber;
  }
  
  public UserDTO fromEntity(UserEntity user) {
    UserDTO userDto  = new UserDTO();
    userDto.setEmail(user.getEmail());
    userDto.setUserName(user.getUserName());
    userDto.setFirstName(user.getFirstName());
    userDto.setLastName(user.getLastName());
    userDto.setId(id);
    userDto.setMobileNumber(user.getMobileNumber());
    return userDto;
  }

  public String getJwtToken() {
    return jwtToken;
  }

  public void setJwtToken(String jwtToken) {
    this.jwtToken = jwtToken;
  }

}
