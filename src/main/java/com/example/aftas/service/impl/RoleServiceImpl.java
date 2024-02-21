package com.example.aftas.service.impl;

import com.example.aftas.domain.Role;
import com.example.aftas.repository.RoleRepository;
import com.example.aftas.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role save(Role role) {

        return roleRepository.save(role);
    }

    @Override
    public void saveAll(List<Role> roles) {
        roleRepository.saveAll(roles);
    }

    @Override
    public Optional<Role> findByAuthority(String authority) {
        return roleRepository.findByAuthority(authority);
    }

    @Override
    public void delete(Role role) {
        roleRepository.delete(role);
    }

    @Override
    public int count() {
        return (int) roleRepository.count();
    }

    @Override
    public Optional<Role> findById(Long id) {
        return roleRepository.findById(id);
    }
}
