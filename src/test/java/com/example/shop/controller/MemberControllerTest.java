package com.example.shop.controller;

import com.example.shop.dto.MemberFormDto;
import com.example.shop.entity.Member;
import com.example.shop.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestPropertySource(locations="classpath:application-test.properties")
class MemberControllerTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * This function tests if sign in works when user tries to sign in with valid
     * email & password that do exist.
     */
    @Test
    @DisplayName("Sign in Test - Success")
    public void signInSuccessTest() throws Exception{
        String email = "test@email.com";
        String password = "1234";
        this.createMember(email, password);

        mockMvc.perform(formLogin("/members/login")
                        .userParameter("email").user(email).password(password))
                .andExpect(SecurityMockMvcResultMatchers.authenticated());
    }

    /**
     * This function tests if sign in works when user tries to sign in with invalid
     * email & password that doesn't exist.
     */
    @Test
    @DisplayName("Sign in Test - Fail")
    public void signInFailTest() throws Exception{
        String email = "test@email.com";
        String password = "Invalid password";
        this.createMember(email, password);

        mockMvc.perform(formLogin("/members/login")
                        .userParameter("email").user(email).password(password))
                .andExpect(SecurityMockMvcResultMatchers.unauthenticated());
    }

    /**
     * This function creates Member Object and stores it to database for testing.
     */
    public void createMember(String email, String password) { // 3
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setName("Hojun Lee");
        memberFormDto.setEmail("test@email.com");
        memberFormDto.setPassword("1234");
        memberFormDto.setAddress("Toronto, Ontario");

        Member member = Member.createMember(memberFormDto, passwordEncoder);
        memberService.saveMember(member);
    }

}