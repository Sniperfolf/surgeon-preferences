package com.iodine.surgeon_preferences.repository;



import com.iodine.surgeon_preferences.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;



import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}