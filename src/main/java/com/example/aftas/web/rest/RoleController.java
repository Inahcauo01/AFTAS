package com.example.aftas.web.rest;

import com.example.aftas.domain.Role;
import com.example.aftas.seeder.RoleSeeder;
import com.example.aftas.service.RoleService;
import com.example.aftas.utils.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/role")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;
    private final RoleSeeder roleSeeder;

    @GetMapping
    public ResponseEntity<Response<List<Role>>> getAllRoles() {
        Response<List<Role>> response = new Response<>();
        List<Role> roles = roleService.findAll();
        response.setResult(roles);
        if (roles.isEmpty()) {
            response.setMessage("There are no roles in the database");
        } else {
            response.setMessage("Role list retrieved successfully");
        }
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/seed")
    public ResponseEntity<Response<List<Role>>> seed() {
        roleSeeder.seedRoles();
        Response<List<Role>> response = new Response<>();
        response.setResult(roleService.findAll());
        response.setMessage("Roles seeded successfully");
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response<String>> deleteRole(@PathVariable Long id) {
        Response<String> response = new Response<>();
        roleService.delete(roleService.findById(id).get());
        response.setMessage("Role deleted successfully");
        return ResponseEntity.ok().body(response);
    }
}
