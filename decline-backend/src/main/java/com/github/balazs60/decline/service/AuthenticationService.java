package com.github.balazs60.decline.service;


import com.github.balazs60.decline.config.JwtService;
import com.github.balazs60.decline.controller.AuthenticationResponse;
import com.github.balazs60.decline.controller.RegisterRequest;
import com.github.balazs60.decline.exception.EmptyInputException;
import com.github.balazs60.decline.exception.PasswordNotValidException;
import com.github.balazs60.decline.exception.UserNameAlreadyExistException;
import com.github.balazs60.decline.exception.UserNotFoundException;
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
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final MemberDetailsService memberDetailsService;

    public AuthenticationResponse register(RegisterRequest request) {

        if (request.getName().length() == 0 ||
                request.getEmail().length() == 0 ||
                request.getPassword().length() == 0
        ) {
            throw new EmptyInputException("601", "Input field is empty");
        } else if(request.getPassword().length() > 0 && request.getPassword().length() < 6){
            throw new PasswordNotValidException("400", "Wrong password format");
        }

        List<String> MemberNames = memberRepository.findAll().stream().map(member -> member.getName()).collect(Collectors.toList());
        for (String name : MemberNames) {
            if (name.equals(request.getName())) {
                throw new UserNameAlreadyExistException("409","Username already exist");
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

        if (request.getName().isEmpty() || request.getPassword().isEmpty()) {
            throw new EmptyInputException("601", "Input field is empty");
        }
        var user = memberRepository.findMemberByName(request.getName());

        if (user == null) {
            throw new UserNotFoundException("404","User not found with this name");
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getName(),
                        request.getPassword()
                )
        );
        var jwtToken = jwtService.generateToken(memberDetailsService.loadUserByUsername(user.getName()));
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

}
