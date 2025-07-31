package com.example.shop.config;

import com.example.shop.constant.Role;
import com.example.shop.entity.Member;
import com.example.shop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (memberRepository.findByEmail("user@test.com") == null) {
            Member user = new Member();
            user.setName("user");
            user.setEmail("user@test.com");
            user.setAddress("Seoul");
            user.setPassword(passwordEncoder.encode("1111"));
            user.setRole(Role.USER);
            memberRepository.save(user);
        }

        if (memberRepository.findByEmail("admin@test.com") == null) {
            Member user = new Member();
            user.setName("admin");
            user.setEmail("admin@test.com");
            user.setAddress("Seoul");
            user.setPassword(passwordEncoder.encode("1234"));
            user.setRole(Role.ADMIN);
            memberRepository.save(user);
        }
    }
}
