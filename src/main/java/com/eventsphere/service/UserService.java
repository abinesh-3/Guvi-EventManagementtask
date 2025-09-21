package com.eventsphere.service;

import com.eventsphere.model.AppUser;
import com.eventsphere.model.Role;
import com.eventsphere.repository.AppUserRepository;
import com.eventsphere.repository.RoleRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final AppUserRepository users;
    private final RoleRepository roles;
    private final PasswordEncoder encoder;

    @Transactional
    public AppUser register(@Valid AppUser user, boolean admin) {
        user.setPassword(encoder.encode(user.getPassword()));
        Role role = roles.findByName(admin ? "ADMIN" : "USER")
                .orElseGet(() -> roles.save(Role.builder().name(admin ? "ADMIN" : "USER").build()));
        user.getRoles().add(role);
        return users.save(user);
    }

    public boolean existsUsername(String u){ return users.existsByUsername(u); }
    public boolean existsEmail(String e){ return users.existsByEmail(e); }
}
