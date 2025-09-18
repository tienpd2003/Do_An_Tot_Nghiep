package com.shop.auth.repository;

import com.shop.auth.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {
    public Optional<Role> findByRoleName(String name);
}
