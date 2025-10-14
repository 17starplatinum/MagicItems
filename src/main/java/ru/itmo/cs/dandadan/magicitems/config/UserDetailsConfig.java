package ru.itmo.cs.dandadan.magicitems.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class UserDetailsConfig {
    @Value("${user.password.bcrypt}")
    private String userPasswordBcrypt;
    @Bean
    public PasswordEncoder passwordEncoder() {
        // Ch. 2*12*, 06/10/2025
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("Platinummmmm?")
                .password(userPasswordBcrypt)
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }
}
