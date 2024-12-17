package com.iodine.surgeon_preferences.service;


import com.iodine.surgeon_preferences.exception.ResourceNotFoundException;
import com.iodine.surgeon_preferences.model.Surgeon;
import com.iodine.surgeon_preferences.model.User;
import com.iodine.surgeon_preferences.repository.SurgeonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.util.List;

@Service
public class SurgeonService {

    @Autowired
    private SurgeonRepository surgeonRepository;

    @Autowired
    private UserService userService;



    public List<Surgeon> getAllSurgeons() {
        User currentUser = getCurrentUser();
        if (currentUser.isAdmin()) {
            return surgeonRepository.findAll();
        }
        return surgeonRepository.findByCreatedBy(currentUser);
    }
    public List<Surgeon> getSurgeonsByUser(User user) {
        if (user.isAdmin()) {
            return surgeonRepository.findAll();
        }
        return surgeonRepository.findByCreatedBy(user);
    }
    public int getTotalPreferenceCardsCount() {
        return getAllSurgeons().stream()
                .mapToInt(surgeon -> surgeon.getPreferenceCards().size())
                .sum();
    }

    public Surgeon getSurgeonById(Long id) {
        User currentUser = getCurrentUser();
        if (currentUser.isAdmin()) {
            return surgeonRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Surgeon not found"));
        }
        return surgeonRepository.findByIdAndCreatedBy(id, currentUser)
                .orElseThrow(() -> new ResourceNotFoundException("Surgeon not found or access denied"));
    }

    public List<Surgeon> searchSurgeons(String searchTerm) {
        User currentUser = getCurrentUser();
        if (currentUser.isAdmin()) {
            if (searchTerm != null && !searchTerm.isEmpty()) {
                return surgeonRepository.findByLastNameContainingIgnoreCase(searchTerm);
            }
            return surgeonRepository.findAll();
        }
        if (searchTerm != null && !searchTerm.isEmpty()) {
            return surgeonRepository.findByCreatedByAndLastNameContainingIgnoreCase(currentUser, searchTerm);
        }
        return surgeonRepository.findByCreatedBy(currentUser);
    }

    public Surgeon saveSurgeon(Surgeon surgeon) {
        surgeon.setCreatedBy(getCurrentUser());
        return surgeonRepository.save(surgeon);
    }

    public void deleteSurgeon(Long id) {
        Surgeon surgeon = getSurgeonById(id);
        if (!surgeon.getPreferenceCards().isEmpty()) {
            throw new RuntimeException("Cannot delete surgeon with existing preference cards");
        }
        surgeonRepository.delete(surgeon);
    }

    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.getUserByUsername(username);
    }
    public void generatePdfReport(OutputStream outputStream) {
        //Future me problem to deal with
    }

    public void generateExcelReport(OutputStream outputStream) {
    //Future me problem to deal with
    }
}