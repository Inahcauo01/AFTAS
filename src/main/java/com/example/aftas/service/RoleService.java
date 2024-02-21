package com.example.aftas.service;

import com.example.aftas.domain.Role;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface RoleService {
    public List<Role> findAll();
    public Role save(Role role);
    public void saveAll(List<Role> roles);
    public Optional<Role> findByAuthority(String authority);
    public void delete(Role role);

    public int count();

    Optional<Role> findById(Long id);
}
