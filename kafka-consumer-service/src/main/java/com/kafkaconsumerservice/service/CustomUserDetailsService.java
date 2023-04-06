package com.kafkaconsumerservice.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final Map<String, UserDetails> userDetailsMap = new ConcurrentHashMap<>();

    public CustomUserDetailsService() {
        UserDetails eddie = User.withUsername("eddie")
                .password("{noop}1234")
                .authorities("read")
                .build();

        UserDetails tyler = User.withUsername("tyler")
                .password("{noop}4321")
                .authorities("read")
                .build();

        userDetailsMap.put("eddie", eddie);
        userDetailsMap.put("tyler", tyler);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = userDetailsMap.get(username);
        if (userDetails == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        return userDetails;
    }

    public String getIdentityKey(String username) {
        switch (username) {
            case "eddie":
                return "P-0123456789";
            case "tyler":
                return "P-1111111111";
            default:
                return null;
        }
    }
}