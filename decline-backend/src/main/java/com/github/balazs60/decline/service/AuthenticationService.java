package com.github.balazs60.decline.service;


import com.github.balazs60.decline.config.JwtService;
import com.github.balazs60.decline.controller.AuthenticationResponse;
import com.github.balazs60.decline.controller.RegisterRequest;
import com.github.balazs60.decline.model.members.Member;
import com.github.balazs60.decline.model.members.Role;
import com.github.balazs60.decline.repositories.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private  final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final MemberDetailsService memberDetailsService;
    public AuthenticationResponse register(RegisterRequest request) {

        List<String> MemberNames=memberRepository.findAll().stream().map(member -> member.getName()).collect(Collectors.toList());
        for (String name:MemberNames){
            if(name.equals(request.getName())){
                System.out.println(("Mar can ilyen Member"));
                return AuthenticationResponse.builder().token("fail").build();
            }
        }

        var user = Member.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        memberRepository.save(user);
        var jwtToken = jwtService.generateToken(memberDetailsService.loadUserByUsername(user.getName()));
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(RegisterRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getName(),
                        request.getPassword()
                )
        );
        var user = memberRepository.findMemberByName(request.getName());
        var jwtToken = jwtService.generateToken(memberDetailsService.loadUserByUsername(user.getName()));
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

}
