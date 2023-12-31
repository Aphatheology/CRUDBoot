package com.example.crudboot.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {
    @NotBlank(message = "Username cannot be blank")
    private String username;

    @Size(min = 6, max = 20, message = "Invalid password, password must be between 6 to 20 characters")
    @NotBlank(message = "Password can not be blank")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{6,20}$", message = "Invalid password, it must have at least one lower case, upper case and number")
    private String password;
}
