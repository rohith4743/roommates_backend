package com.rohithkankipati.roommates.entity;

import java.time.LocalDateTime;
import java.util.Set;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import com.rohithkankipati.roommates.dto.UserDTO;
import com.rohithkankipati.roommates.model.UserRole;

@Document(collection = "users")
public class UserEntity {
  
  @Id
  private String id;
  
  @Indexed(unique = true)
  private String email;
  
  @Field(name = "username")
  @Indexed(unique = true)
  private String userName;
  
  @Field(name = "firstname")
  private String firstName;
  
  @Field(name = "lastname")
  private String lastName;
  
  private String password;
  
  @CreatedDate
  private LocalDateTime dateCreated;
  
  @LastModifiedDate
  private LocalDateTime lastModified;
  
  @Field(name = "mobile")
  @Indexed(unique = true)
  private String mobileNumber;
  
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

  public LocalDateTime getDateCreated() {
    return dateCreated;
  }

  public void setDateCreated(LocalDateTime dateCreated) {
    this.dateCreated = dateCreated;
  }

  public LocalDateTime getLastModified() {
    return lastModified;
  }

  public void setLastModified(LocalDateTime lastModified) {
    this.lastModified = lastModified;
  }

  public String getMobileNumber() {
    return mobileNumber;
  }

  public void setMobileNumber(String mobileNumber) {
    this.mobileNumber = mobileNumber;
  }

  public UserEntity(UserDTO userDTO) {
    this.id = userDTO.getId();
    this.email = userDTO.getEmail();
    this.userName = userDTO.getUserName();
    this.firstName = userDTO.getFirstName();
    this.lastName = userDTO.getLastName();
    this.password = userDTO.getPassword();
    this.roles = userDTO.getRoles();
    this.mobileNumber = userDTO.getMobileNumber();
  }
  
  public UserDTO toUserDTO() {
    UserDTO userDTO = new UserDTO();
    userDTO.setId(this.id);
    userDTO.setEmail(this.email);
    userDTO.setUserName(this.userName);
    userDTO.setFirstName(this.firstName);
    userDTO.setLastName(this.lastName);

    userDTO.setRoles(this.roles);
    userDTO.setMobileNumber(this.mobileNumber);
    return userDTO;
}

  public Set<UserRole> getRoles() {
    return roles;
  }

  public void setRoles(Set<UserRole> roles) {
    this.roles = roles;
  }


}
