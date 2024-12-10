package com.iodine.surgeon_preferences.service;


import com.iodine.surgeon_preferences.model.PreferenceCard;
import com.iodine.surgeon_preferences.repository.PreferenceCardRepository;
import com.iodine.surgeon_preferences.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PreferenceCardService {

    @Autowired
    private PreferenceCardRepository preferenceCardRepository;

    public List<PreferenceCard> getPreferenceCardsBySurgeon(Long surgeonId) {
        return preferenceCardRepository.findBySurgeonId(surgeonId);
    }

    public PreferenceCard getPreferenceCardById(Long id) {
        return preferenceCardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Preference card not found with id: " + id));
    }

    public PreferenceCard savePreferenceCard(PreferenceCard preferenceCard) {
        return preferenceCardRepository.save(preferenceCard);
    }

    public void deletePreferenceCard(Long id) {
        preferenceCardRepository.deleteById(id);
    }

    public List<PreferenceCard> searchPreferenceCards(Long surgeonId, String procedureName) {
        if (procedureName != null && !procedureName.isEmpty()) {
            return preferenceCardRepository.findBySurgeonIdAndProcedureNameContainingIgnoreCase(surgeonId, procedureName);
        }
        return preferenceCardRepository.findBySurgeonId(surgeonId);
    }
}