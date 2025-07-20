package com.example.shop.service;

import com.example.shop.entity.Member;
import com.example.shop.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    /**
     * This method saves Member entity to MemberRepository
     * @return Member entity that is saved to MemberRepository
     */
    public Member saveMember(Member member){
        validateDuplicateMember(member);
        return memberRepository.save(member);
    }

    /**
     * Checks whether an account already exists with the email provided by the user during sign-up.
     * Throws an exception if a duplicate account is found.
     */
    private void validateDuplicateMember(Member member){

        if (memberRepository.findByEmail(member.getEmail()) != null){
           throw new IllegalStateException("Account with this email already exists");
        }
    }
}
