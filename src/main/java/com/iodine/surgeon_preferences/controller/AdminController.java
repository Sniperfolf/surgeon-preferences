package com.iodine.surgeon_preferences.controller;

import com.iodine.surgeon_preferences.model.PreferenceCard;
import com.iodine.surgeon_preferences.model.Surgeon;
import com.iodine.surgeon_preferences.model.User;
import com.iodine.surgeon_preferences.service.PreferenceCardService;
import com.iodine.surgeon_preferences.service.SurgeonService;
import com.iodine.surgeon_preferences.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private UserService userService;
    @Autowired
    private SurgeonService surgeonService;
    @Autowired
    private PreferenceCardService preferenceCardService;

    @GetMapping("/users")
    public String listUsers(Model model) {
        // Add all necessary statistics for admin overview
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("userCount", userService.getAllUsers().size());
        model.addAttribute("surgeonCount", surgeonService.getAllSurgeons().size());
        model.addAttribute("cardCount", surgeonService.getTotalPreferenceCardsCount());

        return "admin/users";  // This will display your admin dashboard
    }
    @PostMapping("/users/{id}/make-admin")
    public String makeUserAdmin(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            userService.makeUserAdmin(id);
            redirectAttributes.addFlashAttribute("successMessage", "User has been made admin");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/admin/users";
    }

    @PostMapping("/users/{id}/remove-admin")
    public String removeAdminRole(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            userService.removeAdminRole(id);
            redirectAttributes.addFlashAttribute("successMessage", "Admin role has been removed");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/admin/users";
    }

    @GetMapping("/users/{id}")
    public String viewUser(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id);
        List<Surgeon> surgeons = surgeonService.getSurgeonsByUser(user);
        int totalPreferenceCards = surgeons.stream()
                .mapToInt(s -> s.getPreferenceCards().size())
                .sum();

        model.addAttribute("user", user);
        model.addAttribute("surgeons", surgeons);
        model.addAttribute("totalPreferenceCards", totalPreferenceCards);
        return "admin/user-detail";
    }
    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        model.addAttribute("userCount", userService.getAllUsers().size());
        model.addAttribute("surgeonCount", surgeonService.getAllSurgeons().size());
        model.addAttribute("cardCount", preferenceCardService.getAllPreferenceCards().size());
        return "admin/dashboard";
    }

    @GetMapping("/surgeons")
    public String listAllSurgeons(Model model) {
        List<Surgeon> surgeons = surgeonService.getAllSurgeons();
        model.addAttribute("surgeons", surgeons);
        return "admin/surgeons-list";
    }

    @GetMapping("/surgeons/{surgeonId}")
    public String viewSurgeonDetails(@PathVariable Long surgeonId, Model model) {
        Surgeon surgeon = surgeonService.getSurgeonById(surgeonId);
        model.addAttribute("surgeon", surgeon);
        model.addAttribute("preferenceCards", surgeon.getPreferenceCards());
        return "admin/surgeon-detail";
    }

    @GetMapping("/surgeons/{surgeonId}/cards/{cardId}/edit")
    public String editPreferenceCard(@PathVariable Long surgeonId,
                                     @PathVariable Long cardId,
                                     Model model) {
        PreferenceCard card = preferenceCardService.getPreferenceCardById(cardId);
        model.addAttribute("card", card);
        model.addAttribute("surgeonId", surgeonId);
        return "admin/edit-preference-card";
    }


    @PostMapping("/surgeons/{surgeonId}/cards/{cardId}")
    public String updatePreferenceCard(@PathVariable Long surgeonId,
                                       @PathVariable Long cardId,
                                       @ModelAttribute PreferenceCard card) {
        preferenceCardService.updatePreferenceCard(cardId, card);
        return "redirect:/admin/surgeons/" + surgeonId;
    }
}
