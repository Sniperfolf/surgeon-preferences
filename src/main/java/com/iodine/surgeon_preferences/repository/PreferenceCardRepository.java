package com.iodine.surgeon_preferences.repository;

import com.iodine.surgeon_preferences.model.PreferenceCard;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PreferenceCardRepository extends JpaRepository<PreferenceCard, Long> {
    List<PreferenceCard> findBySurgeonId(Long surgeonId);
    List<PreferenceCard> findBySurgeonIdAndProcedureNameContainingIgnoreCase(Long surgeonId, String procedureName);
}