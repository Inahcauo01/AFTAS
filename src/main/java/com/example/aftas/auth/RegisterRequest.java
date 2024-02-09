package com.example.aftas.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {

    private Integer num;
    private String name;
    private String familyName;
    private String nationality;
    private String identityDocument;
    private String identityNumber;
    private LocalDate accessionDate;

    private String username;
    private String password;
    private Set<String> roles;

    // I want a Json
// {
//     "num": 1,
//     "name": "John",
//     "familyName": "Doe",
//     "national
//     "identityDocument": "Passport",
//     "identityNumber": "123456789",
//     "accessionDate": "2021-01-01",
//     "username": "john.doe",
//     "password": "password",
//     "roles": ["ROLE_USER"]
// }
}
