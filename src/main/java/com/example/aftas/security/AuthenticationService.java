package com.example.aftas.security;

import com.example.aftas.domain.enums.IdentityDocumentType;
import com.example.aftas.repository.MemberRespository;
import com.example.aftas.auth.AuthenticationResponse;
import com.example.aftas.auth.LoginRequest;
import com.example.aftas.auth.RegisterRequest;
import com.example.aftas.domain.Role;
import com.example.aftas.domain.Member;
import com.example.aftas.repository.RoleRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final MemberRespository memberRespository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;

    public AuthenticationResponse register(RegisterRequest request) {
        // create a user out of the request
        var user = Member.builder()
                .num(request.getNum())
                .name(request.getName())
                .familyName(request.getFamilyName())
                .identityDocument(IdentityDocumentType.valueOf(request.getIdentityDocument()))
                .identityNumber(request.getIdentityNumber())
                .nationality(request.getNationality())
                .accessionDate(request.getAccessionDate())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .enabled(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .build();


        // If the user has no roles, set the default role to the user
        Set<String> roles = request.getRoles();
        if (roles == null || roles.isEmpty() || roles.contains("ROLE_USER")) {
            user.setAuthorities(Set.of(getOrCreateRole("ROLE_USER")));
            user.setEnabled(false);
        } else {
            user.setEnabled(true);
            user.setAuthorities(getOrCreateRoles(roles));
        }

        memberRespository.save(user);
        // generate & return a token for the user
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse login(LoginRequest request) {
        // authenticate the user
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        // find the user by username
        var user = memberRespository.findByUsername(request.getUsername()).orElseThrow();
        // generate & return token for the user
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }


    private Set<Role> getOrCreateRoles(Set<String> roleNames) {
        return roleNames.stream()
                .map(this::getOrCreateRole)
                .collect(Collectors.toSet());
    }

    private Role getOrCreateRole(String roleName) {
        // Check if the role exists
        return roleRepository.findByAuthority(roleName)
                .orElseGet(() -> roleRepository.save(Role.builder().authority(roleName).build()));
    }


    public AuthenticationResponse refreshToken(String refreshToken) {
        var username = jwtService.extractUsername(refreshToken);

        var user = memberRespository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User not found with username: " + username));

        if (jwtService.isTokenValid(refreshToken, user)) {
            return AuthenticationResponse.builder()
                    .token(jwtService.generateToken(user))
                    .refreshToken(jwtService.generateRefreshToken(user))
                    .build();
        }
        else {
            throw new RuntimeException("Invalid refresh token");
        }
    }
}
