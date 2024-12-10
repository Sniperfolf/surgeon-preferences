package com.iodine.surgeon_preferences.controller;

import com.iodine.surgeon_preferences.model.Surgeon;
import com.iodine.surgeon_preferences.service.SurgeonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/surgeons")
public class SurgeonController {

    @Autowired
    private SurgeonService surgeonService;

    @GetMapping("")
    public String listSurgeons(Model model, @RequestParam(required = false) String search) {
        if (search != null && !search.isEmpty()) {
            model.addAttribute("surgeons", surgeonService.searchSurgeons(search));
        } else {
            model.addAttribute("surgeons", surgeonService.getAllSurgeons());
        }
        return "surgeon/list";
    }


    @GetMapping("/form")
    public String showCreateForm(Model model) {
        if (!model.containsAttribute("surgeon")) {
            model.addAttribute("surgeon", new Surgeon());
        }
        return "surgeon/form";
    }

    @PostMapping("/save")
    public String saveSurgeon(@Valid @ModelAttribute("surgeon") Surgeon surgeon,
                              BindingResult result,
                              Model model) {
        if (result.hasErrors()) {
            return "surgeon/form";
        }

        surgeonService.saveSurgeon(surgeon);
        return "redirect:/surgeons";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("surgeon", surgeonService.getSurgeonById(id));
        return "surgeon/form";
    }

    @GetMapping("/{id}")
    public String viewSurgeon(@PathVariable Long id, Model model) {
        Surgeon surgeon = surgeonService.getSurgeonById(id);
        model.addAttribute("surgeon", surgeon);
        return "surgeon/view";
    }

    @PostMapping("/delete/{id}")
    public String deleteSurgeon(@PathVariable Long id) {
        surgeonService.deleteSurgeon(id);
        return "redirect:/surgeons";
    }
}