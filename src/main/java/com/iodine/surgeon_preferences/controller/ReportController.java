package com.iodine.surgeon_preferences.controller;

import com.iodine.surgeon_preferences.service.PreferenceCardService;
import com.iodine.surgeon_preferences.service.SurgeonService;
import com.iodine.surgeon_preferences.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/reports")
@PreAuthorize("hasRole('ADMIN')")
public class ReportController {

    @Autowired
    private SurgeonService surgeonService;
    @Autowired
    private UserService userService;
    @Autowired
    private PreferenceCardService preferenceCardService;


    @GetMapping("/print/{reportType}")
    public String printReport(@PathVariable String reportType, Model model) {
        switch (reportType) {
            case "surgeons":
                model.addAttribute("surgeons", surgeonService.getAllSurgeons());
                return "admin/reports/print/surgeons";
            case "users":
                model.addAttribute("users", userService.getAllUsers());
                return "admin/reports/print/users";
            case "preference-cards":
                model.addAttribute("preferenceCards", preferenceCardService.getAllPreferenceCards());
                return "admin/reports/print/preference-cards";
                default:
                throw new IllegalArgumentException("Unknown report type: " + reportType);
        }
    }

    @GetMapping
    public String showReportsPage(Model model) {
        return "admin/reports/select";
    }
}