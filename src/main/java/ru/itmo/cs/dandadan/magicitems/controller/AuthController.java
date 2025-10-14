package ru.itmo.cs.dandadan.magicitems.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itmo.cs.dandadan.magicitems.dto.auth.AuthRequestDto;
import ru.itmo.cs.dandadan.magicitems.dto.auth.AuthResponseDto;
import ru.itmo.cs.dandadan.magicitems.service.AuthService;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public AuthResponseDto login(@RequestBody AuthRequestDto authRequestDto) {
        return new AuthResponseDto(authService.getJwt(authRequestDto));
    }
}
