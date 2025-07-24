package com.example.shop.config;

import com.example.shop.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig{

    @Autowired
    MemberService memberService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                // Add csrf setting
                .csrf(csrf -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) // CSRF 토큰을 쿠키에 저장
                )

                // Filter for CSRF creation
                .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)

                // Authorization Setting
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/js/**", "/img/**", "/", "/members/**", "/item/**", "/images/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )

                // Login Setting
                .formLogin(formLogin -> formLogin
                        .loginPage("/members/login")
                        .defaultSuccessUrl("/", true)
                        .usernameParameter("email")
                        .failureUrl("/members/login/error")
                )
                // Logout setting
                .logout(logout -> logout
                        .logoutUrl("/members/logout")
                        .logoutSuccessUrl("/")
                )

                // Setting for Authentication Fail
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(
                                new org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint("/members/login"))
                )

                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // use BCrypt when encode
    }
}
