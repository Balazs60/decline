package com.github.balazs60.decline.service;

import com.github.balazs60.decline.repositories.MemberRepository;
import org.springframework.stereotype.Service;

import com.github.balazs60.decline.model.members.Member;

@Service
public class MemberService {

    private MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member getMemberByName(String memberName){
        return memberRepository.findMemberByName(memberName);
    }

}
