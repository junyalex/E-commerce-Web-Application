package com.example.shop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig{

//    @Autowired
//    MemberService memberService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.formLogin(formLogin -> formLogin
                        .loginPage("/members/login")
                        .defaultSuccessUrl("/")
                        .usernameParameter("email") // use email as parameter when login
                        .failureUrl("/members/login/error")
                )
                // Settings for Authorization
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/css/**", "/js/**", "/img/**", "/", "/members/**", "/item/**", "/images/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .logout(logout -> logout
                        .logoutUrl("/members/logout")
                        .logoutSuccessUrl("/")
                );

        // Redirects unauthenticated users to "/members/login" when they try to access protected pages
        http.exceptionHandling(exception -> exception
                .authenticationEntryPoint(new org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint("/members/login"))
        );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // use BCrypt when encode
    }
}
