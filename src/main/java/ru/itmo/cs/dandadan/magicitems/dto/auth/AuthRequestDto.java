package ru.itmo.cs.dandadan.magicitems.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AuthRequestDto {
    @NotBlank
    @Size(min = 3, max = 100)
    String username;

    @NotBlank
    @Size(min = 8, max = 128)
    String password;
}
