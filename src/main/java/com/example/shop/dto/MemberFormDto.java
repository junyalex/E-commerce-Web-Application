package com.example.shop.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter @Setter
public class MemberFormDto {

    @NotBlank
    private String name;

    @NotEmpty
    private String email;

    @NotEmpty @Length(min=8, max=16)
    private String password;

    @NotEmpty
    private String address;
}
