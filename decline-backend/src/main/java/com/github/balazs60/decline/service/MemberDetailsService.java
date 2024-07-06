package com.github.balazs60.decline.service;


import com.github.balazs60.decline.model.members.Member;
import com.github.balazs60.decline.repositories.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberDetailsService implements UserDetailsService {

    private MemberRepository memberRepository;

    @Autowired
    public MemberDetailsService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }









    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member memberByName = memberRepository.findMemberByName(username);
        User user = new User(username, memberByName.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_"+memberByName.getRole().name())));
        return user;
    }
}
