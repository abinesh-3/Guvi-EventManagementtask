package com.eventsphere.service;

import com.eventsphere.model.AppUser;
import com.eventsphere.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DbUserDetailsService implements UserDetailsService {

    private final AppUserRepository users;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser u = users.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found"));
        Set<GrantedAuthority> authorities = u.getRoles().stream()
                .map(r -> new SimpleGrantedAuthority("ROLE_" + r.getName()))
                .collect(Collectors.toSet());
        return new User(u.getUsername(), u.getPassword(), u.isEnabled(), true, true, true, authorities);
    }
}
