package com.dc.springsecurityjwt.controller;

import com.dc.springsecurityjwt.repository.UserRepository;
import com.dc.springsecurityjwt.service.CustomUserDetailsService;
import com.dc.springsecurityjwt.util.JwtUtils;
import com.dc.springsecurityjwt.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository uRepository;

    @PostMapping("/login")
    public String login(@RequestBody User user) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        } catch (AuthenticationException e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        return jwtUtils.generateToken(userDetails);
    }

    @GetMapping("/login")
    public String login() throws Exception {

        return "jwtUtils.generateToken(userDetails)";
    }
}
