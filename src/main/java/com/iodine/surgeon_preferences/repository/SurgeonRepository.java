package com.iodine.surgeon_preferences.repository;

import com.iodine.surgeon_preferences.model.Surgeon;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SurgeonRepository extends JpaRepository<Surgeon, Long> {
    List<Surgeon> findBySpecialty(String specialty);
    List<Surgeon> findByLastNameContainingIgnoreCase(String lastName);
}
