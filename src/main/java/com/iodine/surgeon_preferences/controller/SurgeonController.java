package com.iodine.surgeon_preferences.controller;

import com.iodine.surgeon_preferences.exception.SurgeonDeleteException;
import com.iodine.surgeon_preferences.model.Surgeon;
import com.iodine.surgeon_preferences.service.SurgeonService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

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

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Surgeon surgeon = surgeonService.getSurgeonById(id);
        model.addAttribute("surgeon", surgeon);
        return "surgeon/form"; // Using the same form for create and edit but plan for separate ones in the future.
    }

    @GetMapping("/{id}")
    public String viewSurgeon(@PathVariable Long id, Model model) {
        Surgeon surgeon = surgeonService.getSurgeonById(id);
        model.addAttribute("surgeon", surgeon);
        return "surgeon/view";
    }

    @PostMapping("/{id}/delete")
    public String deleteSurgeon(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            surgeonService.deleteSurgeon(id);
            redirectAttributes.addFlashAttribute("successMessage", "Surgeon successfully deleted.");
        } catch (SurgeonDeleteException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/surgeons";
    }
    @GetMapping("/export/pdf")
    public void exportPDF(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=surgeons.pdf");
        surgeonService.generatePdfReport(response.getOutputStream());
    }
    @GetMapping("/export/excel")
    public void exportExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=surgeons.xlsx");
        surgeonService.generateExcelReport(response.getOutputStream());
    }
}