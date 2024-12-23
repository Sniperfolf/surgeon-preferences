package com.iodine.surgeon_preferences.repository;

import com.iodine.surgeon_preferences.model.PreferenceCard;
import com.iodine.surgeon_preferences.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
//Here is some of my inheritance code. Again I have 4 of these set up. I need to clean out the ones that are not being used. From all my revisions.
public interface PreferenceCardRepository extends JpaRepository<PreferenceCard, Long> {
    List<PreferenceCard> findBySurgeonId(Long surgeonId);
    List<PreferenceCard> findBySurgeonIdAndCreatedBy(Long surgeonId, User user);
    Optional<PreferenceCard> findByIdAndCreatedBy(Long id, User user);
    List<PreferenceCard> findBySurgeonIdAndCreatedByAndProcedureNameContainingIgnoreCase(
            Long surgeonId, User user, String procedureName);
    List<PreferenceCard> findBySurgeonIdAndProcedureNameContainingIgnoreCase(Long surgeonId, String searchTerm);

}