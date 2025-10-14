package ru.itmo.cs.dandadan.magicitems.service;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.itmo.cs.dandadan.magicitems.dto.auth.AuthRequestDto;
import ru.itmo.cs.dandadan.magicitems.security.JwtUtils;

@Service
@AllArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public String getJwt(AuthRequestDto authRequestDto) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(authRequestDto.getUsername(), authRequestDto.getPassword());
        authenticationManager.authenticate(token);
        return jwtUtils.generateToken(authRequestDto.getUsername());
    }
}
