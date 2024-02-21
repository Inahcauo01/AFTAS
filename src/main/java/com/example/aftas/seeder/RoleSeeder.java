package com.example.aftas.seeder;

import com.example.aftas.domain.Role;
import com.example.aftas.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class RoleSeeder {
    private final RoleService roleService;

    public void seedRoles() {
        if (roleService.count() == 0) { // if there are no roles in the database
            roleService.saveAll(
                    Arrays.asList(
                            Role.builder().authority("ROLE_USER").build(),
                            Role.builder().authority("ROLE_JURY").build(),
                            Role.builder().authority("ROLE_MANAGER").build()
                    )
            );
        }
    }
}
