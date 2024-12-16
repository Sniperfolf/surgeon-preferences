package com.iodine.surgeon_preferences.controller;

import com.iodine.surgeon_preferences.model.Surgeon;
import com.iodine.surgeon_preferences.model.User;
import com.iodine.surgeon_preferences.service.SurgeonService;
import com.iodine.surgeon_preferences.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private UserService userService;
    @Autowired
    private SurgeonService surgeonService;

    @GetMapping("/users")
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin/users";
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
}
