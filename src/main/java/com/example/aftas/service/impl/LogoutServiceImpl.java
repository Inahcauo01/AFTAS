package com.example.aftas.service.impl;

import com.example.aftas.domain.Member;
import com.example.aftas.repository.MemberRespository;
import com.example.aftas.security.JwtService;
import com.example.aftas.service.LogoutService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutServiceImpl implements LogoutService {

    private final JwtService jwtService;
    private final MemberRespository userRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        // check if the user is not already authenticated
        if(StringUtils.isEmpty(authHeader) || !StringUtils.startsWith(authHeader,"Bearer ")){
            return;
        }

        jwt = authHeader.substring(7); // remove the Bearer prefix
        username =jwtService.extractUserName(jwt); // extract the username from the token
        System.out.println("username : "+username);
        Member user = userRepository.findByUsername(username).orElse(null);
        System.out.println("user : "+user.getFamilyName());
        if(user != null){
//            user.setCredentialsNonExpired(false);
//            userRepository.save(user);
            System.out.println("ok");
        }
    }
}
