package com.iodine.surgeon_preferences.controller;

import com.iodine.surgeon_preferences.model.PreferenceCard;
import com.iodine.surgeon_preferences.model.Surgeon;
import com.iodine.surgeon_preferences.service.PreferenceCardService;
import com.iodine.surgeon_preferences.service.SurgeonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/surgeons/{surgeonId}/preferences")
public class PreferenceCardController {

    @Autowired
    private PreferenceCardService preferenceCardService;

    @Autowired
    private SurgeonService surgeonService;

    @GetMapping("")
    public String listPreferenceCards(@PathVariable Long surgeonId,
                                      @RequestParam(required = false) String search,
                                      Model model) {
        Surgeon surgeon = surgeonService.getSurgeonById(surgeonId);
        model.addAttribute("surgeon", surgeon);
        model.addAttribute("preferenceCards",
                preferenceCardService.searchPreferenceCards(surgeonId, search));
        return "preference/list";
    }

    @GetMapping("/new")
    public String showCreateForm(@PathVariable Long surgeonId, Model model) {
        Surgeon surgeon = surgeonService.getSurgeonById(surgeonId);
        PreferenceCard preferenceCard = new PreferenceCard();
        preferenceCard.setSurgeon(surgeon);
        model.addAttribute("surgeon", surgeon);
        model.addAttribute("preferenceCard", preferenceCard);
        return "preference/form";
    }

    @PostMapping("/save")
    public String savePreferenceCard(@PathVariable Long surgeonId,
                                     @ModelAttribute PreferenceCard preferenceCard) {
        Surgeon surgeon = surgeonService.getSurgeonById(surgeonId);
        preferenceCard.setSurgeon(surgeon);
        preferenceCardService.savePreferenceCard(preferenceCard);
        return "redirect:/surgeons/" + surgeonId + "/preferences";
    }

    @GetMapping("/{id}")
    public String viewPreferenceCard(@PathVariable Long surgeonId,
                                     @PathVariable Long id,
                                     Model model) {
        model.addAttribute("surgeon", surgeonService.getSurgeonById(surgeonId));
        model.addAttribute("preferenceCard", preferenceCardService.getPreferenceCardById(id));
        return "preference/view";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long surgeonId,
                               @PathVariable Long id,
                               Model model) {
        model.addAttribute("surgeon", surgeonService.getSurgeonById(surgeonId));
        model.addAttribute("preferenceCard", preferenceCardService.getPreferenceCardById(id));
        return "preference/form";
    }

    @PostMapping("/{id}/delete")
    public String deletePreferenceCard(@PathVariable Long surgeonId,
                                       @PathVariable Long id) {
        preferenceCardService.deletePreferenceCard(id);
        return "redirect:/surgeons/" + surgeonId + "/preferences";
    }
}
