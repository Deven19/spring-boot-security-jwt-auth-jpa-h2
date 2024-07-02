package com.dc.springsecurityjwt.repository;

import com.dc.springsecurityjwt.entity.Role;
import com.dc.springsecurityjwt.entity.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);
}
