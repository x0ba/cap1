package me.danielx.api.users;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Objects;

@Entity
@Table(
    name = "users",
    indexes = {
      @Index(name = "idx_users_email", columnList = "email", unique = true),
      @Index(name = "idx_users_phone_number", columnList = "phoneNumber", unique = true)
    })
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Size(max = 36)
  @Column(nullable = false, updatable = false, length = 36)
  private String id;

  @NotBlank
  @Size(max = 120)
  @Column(nullable = false, unique = true, length = 120)
  private String email;

  @NotBlank
  @Column(nullable = false)
  private boolean emailVerified = false;

  @Size(max = 120)
  @Column(length = 120)
  private String displayName;

  @Size(max = 32)
  @Column(length = 32)
  private String phoneNumber;

  @NotBlank
  @Column(nullable = false)
  private boolean phoneNumberVerified = false;

  protected User() {}

  public User(
      String id,
      String email,
      boolean emailVerified,
      String displayName,
      String phoneNumber,
      boolean phoneNumberVerified) {
    this.id = id;
    this.email = email;
    this.emailVerified = emailVerified;
    this.displayName = displayName;
    this.phoneNumber = phoneNumber;
    this.phoneNumberVerified = phoneNumberVerified;
  }

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

  public boolean isEmailVerified() {
    return emailVerified;
  }

  public void setEmailVerified(boolean emailVerified) {
    this.emailVerified = emailVerified;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public boolean isPhoneNumberVerified() {
    return phoneNumberVerified;
  }

  public void setPhoneNumberVerified(boolean phoneNumberVerified) {
    this.phoneNumberVerified = phoneNumberVerified;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return emailVerified == user.emailVerified
        && phoneNumberVerified == user.phoneNumberVerified
        && Objects.equals(id, user.id)
        && Objects.equals(email, user.email)
        && Objects.equals(displayName, user.displayName)
        && Objects.equals(phoneNumber, user.phoneNumber);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, email, emailVerified, displayName, phoneNumber, phoneNumberVerified);
  }
}
