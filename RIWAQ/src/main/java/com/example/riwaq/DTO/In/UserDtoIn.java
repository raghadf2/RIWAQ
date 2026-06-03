package com.example.riwaq.DTO.In;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDtoIn {
    @NotEmpty
    private String name;
    @NotEmpty(message = "Username is required")
    @Size(min = 3, max = 20, message = "username must be between 3 and 20 characters")
    private String username;

    @NotEmpty(message = "Password is required")
    @Size(min = 6 , message = "password must be more than 5 characters")
    private String password;

    @NotEmpty(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    private String email;

    @Pattern(regexp = "^(?:\\+?966|0)?5[0-9]{8}$", message = "Invalid Saudi mobile number format")
    private String phoneNumber;
}
