package com.iodine.surgeon_preferences.service;


import com.iodine.surgeon_preferences.model.PreferenceCard;
import com.iodine.surgeon_preferences.model.User;
import com.iodine.surgeon_preferences.repository.PreferenceCardRepository;
import com.iodine.surgeon_preferences.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class PreferenceCardService {

    @Autowired
    private PreferenceCardRepository preferenceCardRepository;

    @Autowired
    private UserService userService;

    public List<PreferenceCard> getPreferenceCardsBySurgeon(Long surgeonId) {
        User currentUser = getCurrentUser();
        return preferenceCardRepository.findBySurgeonIdAndCreatedBy(surgeonId, currentUser);
    }
    public PreferenceCard updatePreferenceCard(Long cardId, PreferenceCard updatedCard) {
        PreferenceCard existingCard = preferenceCardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Preference card not found with id: " + cardId));

        // Update the fields
        existingCard.setProcedureName(updatedCard.getProcedureName());
        existingCard.setGloveSize(updatedCard.getGloveSize());
        existingCard.setInstruments(updatedCard.getInstruments());
        existingCard.setSutures(updatedCard.getSutures());
        existingCard.setNotes(updatedCard.getNotes());

        // The @PreUpdate annotation will handle lastUpdated
        return preferenceCardRepository.save(existingCard);
    }


    public PreferenceCard savePreferenceCard(PreferenceCard card) {
        card.setCreatedBy(getCurrentUser());
        return preferenceCardRepository.save(card);
    }

    public PreferenceCard getPreferenceCardById(Long id) {
        User currentUser = getCurrentUser();
        return preferenceCardRepository.findByIdAndCreatedBy(id, currentUser)
                .orElseThrow(() -> new ResourceNotFoundException("Preference card not found or access denied"));
    }
    public List<PreferenceCard> getAllPreferenceCards() {
        return preferenceCardRepository.findAll();
    }


    public List<PreferenceCard> searchPreferenceCards(Long surgeonId, String searchTerm) {
        User currentUser = getCurrentUser();
        if (searchTerm != null && !searchTerm.isEmpty()) {
            return preferenceCardRepository.findBySurgeonIdAndCreatedByAndProcedureNameContainingIgnoreCase(
                    surgeonId, currentUser, searchTerm);
        }
        return preferenceCardRepository.findBySurgeonIdAndCreatedBy(surgeonId, currentUser);
    }

    public void deletePreferenceCard(Long id) {
        User currentUser = getCurrentUser();
        PreferenceCard card = preferenceCardRepository.findByIdAndCreatedBy(id, currentUser)
                .orElseThrow(() -> new ResourceNotFoundException("Preference card not found or access denied"));

        preferenceCardRepository.delete(card);
    }

    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.getUserByUsername(username);
    }
}