package com.eventsphere.repository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.eventsphere.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
