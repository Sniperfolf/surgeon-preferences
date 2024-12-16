package com.iodine.surgeon_preferences.repository;

import com.iodine.surgeon_preferences.model.Surgeon;
import com.iodine.surgeon_preferences.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface SurgeonRepository extends JpaRepository<Surgeon, Long> {
    List<Surgeon> findByCreatedBy(User user);
    Optional<Surgeon> findByIdAndCreatedBy(Long id, User user);
    List<Surgeon> findByCreatedByAndLastNameContainingIgnoreCase(User user, String lastName);
    List<Surgeon> findByLastNameContainingIgnoreCase(String lastName);
}
