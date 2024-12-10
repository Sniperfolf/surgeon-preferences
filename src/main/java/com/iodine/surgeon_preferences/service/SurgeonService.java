package com.iodine.surgeon_preferences.service;

import com.iodine.surgeon_preferences.model.Surgeon;
import com.iodine.surgeon_preferences.repository.SurgeonRepository;
import com.iodine.surgeon_preferences.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SurgeonService {

    @Autowired
    private SurgeonRepository surgeonRepository;

    public List<Surgeon> getAllSurgeons() {
        return surgeonRepository.findAll();
    }

    public Surgeon getSurgeonById(Long id) {
        return surgeonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Surgeon not found with id: " + id));
    }

    public List<Surgeon> searchSurgeons(String lastName) {
        return surgeonRepository.findByLastNameContainingIgnoreCase(lastName);
    }

    public Surgeon saveSurgeon(Surgeon surgeon) {
        return surgeonRepository.save(surgeon);
    }

    public void deleteSurgeon(Long id) {
        if (!surgeonRepository.existsById(id)) {
            throw new ResourceNotFoundException("Surgeon not found with id: " + id);
        }
        surgeonRepository.deleteById(id);
    }
}