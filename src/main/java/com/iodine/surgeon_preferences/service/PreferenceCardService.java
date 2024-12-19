package com.iodine.surgeon_preferences.service;


import com.iodine.surgeon_preferences.model.PreferenceCard;
import com.iodine.surgeon_preferences.model.User;
import com.iodine.surgeon_preferences.repository.PreferenceCardRepository;
import com.iodine.surgeon_preferences.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
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
        User currentUser = userService.getCurrentUser();
        if (currentUser.isAdmin()) {
            return preferenceCardRepository.findBySurgeonId(surgeonId);
        }
        return preferenceCardRepository.findBySurgeonIdAndCreatedBy(surgeonId, currentUser);
    }
    public PreferenceCard updatePreferenceCard(Long cardId, PreferenceCard updatedCard) {
        User currentUser = userService.getCurrentUser();
        PreferenceCard existingCard = preferenceCardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Preference card not found with id: " + cardId));

        // Check if user is admin or card owner
        if (!currentUser.isAdmin() && !existingCard.getCreatedBy().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException("You don't have permission to edit this card");
        }

        existingCard.setProcedureName(updatedCard.getProcedureName());
        existingCard.setGloveSize(updatedCard.getGloveSize());
        existingCard.setInstruments(updatedCard.getInstruments());
        existingCard.setSutures(updatedCard.getSutures());
        existingCard.setNotes(updatedCard.getNotes());

        return preferenceCardRepository.save(existingCard);
    }


    public PreferenceCard savePreferenceCard(PreferenceCard card) {
        card.setCreatedBy(userService.getCurrentUser());  // Changed from getCurrentUser()
        return preferenceCardRepository.save(card);
    }

    public PreferenceCard getPreferenceCardById(Long id) {
        User currentUser = userService.getCurrentUser();
        if (currentUser.isAdmin()) {
            return preferenceCardRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Preference card not found"));
        }
        return preferenceCardRepository.findByIdAndCreatedBy(id, currentUser)
                .orElseThrow(() -> new ResourceNotFoundException("Preference card not found or access denied"));
    }
    public List<PreferenceCard> getAllPreferenceCards() {
        return preferenceCardRepository.findAll();
    }


    public List<PreferenceCard> searchPreferenceCards(Long surgeonId, String searchTerm) {
        User currentUser = userService.getCurrentUser();  // Changed from getCurrentUser()
        if (searchTerm != null && !searchTerm.isEmpty()) {
            return preferenceCardRepository.findBySurgeonIdAndCreatedByAndProcedureNameContainingIgnoreCase(
                    surgeonId, currentUser, searchTerm);
        }
        return preferenceCardRepository.findBySurgeonIdAndCreatedBy(surgeonId, currentUser);
    }

    public void deletePreferenceCard(Long id) {
        User currentUser = userService.getCurrentUser();
        PreferenceCard card = preferenceCardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Preference card not found"));

        if (!currentUser.isAdmin() && !card.getCreatedBy().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException("You don't have permission to delete this card");
        }

        preferenceCardRepository.delete(card);
    }


}