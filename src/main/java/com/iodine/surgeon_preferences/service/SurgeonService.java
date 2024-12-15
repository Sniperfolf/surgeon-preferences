package com.iodine.surgeon_preferences.service;

import com.iodine.surgeon_preferences.exception.SurgeonDeleteException;
import com.iodine.surgeon_preferences.model.Surgeon;
import com.iodine.surgeon_preferences.repository.SurgeonRepository;
import com.iodine.surgeon_preferences.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
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
    @Transactional
    public void deleteSurgeon(Long id) {
        Surgeon surgeon = surgeonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Surgeon not found with id: " + id));

        if (surgeon.getPreferenceCards() != null && !surgeon.getPreferenceCards().isEmpty()) {
            throw new SurgeonDeleteException("Cannot delete surgeon with existing preference cards. Please delete all preference cards first.");
        }

        surgeonRepository.deleteById(id);
    }


    public Surgeon saveSurgeon(Surgeon surgeon) {
        return surgeonRepository.save(surgeon);
    }


    public void generatePdfReport(OutputStream outputStream) {
        // Implementation for PDF generation
        // You might want to use a library like iText or Apache PDFBox
    }

    public void generateExcelReport(OutputStream outputStream) {
        // Implementation for Excel generation
        // You might want to use Apache POI
    }
}