package com.iodine.surgeon_preferences.controller;


import com.iodine.surgeon_preferences.dto.RegistrationDto;
import com.iodine.surgeon_preferences.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        if (!model.containsAttribute("registration")) {
            model.addAttribute("registration", new RegistrationDto());
        }
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("registration") RegistrationDto registration,
                               BindingResult result,
                               RedirectAttributes redirectAttributes) {
        // Validate passwords match
        if (!registration.getPassword().equals(registration.getConfirmPassword())) {
            result.rejectValue("confirmPassword", "error.confirmPassword", "Passwords do not match");
        }

        if (result.hasErrors()) {
            return "auth/register";
        }

        try {
            userService.registerUser(registration);
            redirectAttributes.addFlashAttribute("successMessage", "Registration successful! Please log in.");
            return "redirect:/login";
        } catch (Exception e) {
            result.rejectValue("username", "error.username", "Username already exists");
            return "auth/register";
        }
    }
}
