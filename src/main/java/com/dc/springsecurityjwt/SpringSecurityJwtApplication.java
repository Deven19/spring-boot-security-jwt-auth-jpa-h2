package com.dc.springsecurityjwt;

import com.dc.springsecurityjwt.entity.Role;
import com.dc.springsecurityjwt.entity.RoleName;
import com.dc.springsecurityjwt.entity.User;
import com.dc.springsecurityjwt.repository.RoleRepository;
import com.dc.springsecurityjwt.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class SpringSecurityJwtApplication {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void intUsers(){
        Role roleAdmin = new Role();
        roleAdmin.setName(RoleName.ADMIN);
        roleRepository.save(roleAdmin);

        Role rolePublic = new Role();
        rolePublic.setName(RoleName.ROLE_PUBLIC);
        roleRepository.save(rolePublic);

        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(roleAdmin);

        Set<Role> publicRoles = new HashSet<>();
        publicRoles.add(rolePublic);

        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setRoles(adminRoles);
        userRepository.save(admin);

        User user = new User();
        user.setUsername("user");
        user.setPassword(passwordEncoder.encode("user"));
        user.setRoles(publicRoles);
        userRepository.save(user);

        /*List<User> users = Stream.of(
                new User(101,"Jack","mypassword", "jack@mypassword.com"),
                new User(102,"Jack2","mypassword", "jack2@mypassword.com"),
                new User(103,"Jack3","mypassword", "jack3@mypassword.com")
        ).collect(Collectors.toList());
        userRepository.saveAll(users);*/
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityJwtApplication.class, args);
    }

}
