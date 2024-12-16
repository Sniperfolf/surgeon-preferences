package com.iodine.surgeon_preferences.repository;

import com.iodine.surgeon_preferences.model.PreferenceCard;
import com.iodine.surgeon_preferences.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PreferenceCardRepository extends JpaRepository<PreferenceCard, Long> {
    List<PreferenceCard> findBySurgeonIdAndCreatedBy(Long surgeonId, User user);
    Optional<PreferenceCard> findByIdAndCreatedBy(Long id, User user);
    List<PreferenceCard> findBySurgeonIdAndCreatedByAndProcedureNameContainingIgnoreCase(
            Long surgeonId, User user, String procedureName);
}