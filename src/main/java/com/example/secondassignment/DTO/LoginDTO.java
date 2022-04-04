package com.example.secondassignment.DTO;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class LoginDTO {

    @NotNull(message = "Email is required.")
    @Size(min = 1, message = "Email is required.")
    @Email(message = "Email is not well formatted.")
    private String email;

    @NotNull(message = "Password is required.")
    @Size(min = 6, message = "Password should be at least 6 characters.")
    private String password;
}
