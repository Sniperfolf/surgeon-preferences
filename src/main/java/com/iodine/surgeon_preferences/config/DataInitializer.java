package com.iodine.surgeon_preferences.config;

import com.iodine.surgeon_preferences.model.PreferenceCard;
import com.iodine.surgeon_preferences.model.Role;
import com.iodine.surgeon_preferences.model.Surgeon;
import com.iodine.surgeon_preferences.model.User;
import com.iodine.surgeon_preferences.repository.PreferenceCardRepository;
import com.iodine.surgeon_preferences.repository.RoleRepository;
import com.iodine.surgeon_preferences.repository.SurgeonRepository;
import com.iodine.surgeon_preferences.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private SurgeonRepository surgeonRepository;
    @Autowired
    private PreferenceCardRepository preferenceCardRepository;

    @Override
    public void run(String... args) {
        // Role creation
        if (roleRepository.count() == 0) {
            Role adminRole = new Role();
            adminRole.setName("ROLE_ADMIN");
            roleRepository.save(adminRole);

            Role userRole = new Role();
            userRole.setName("ROLE_USER");
            roleRepository.save(userRole);

            // Create admin user for testing
            User admin = new User();
            admin.setUsername("Admin");
            admin.setFirstName("Corey");
            admin.setLastName("Carter");
            admin.setPassword(passwordEncoder.encode("*!T$*$6M5722%e"));
            admin.setEmail("ccar944@wgu.edu");
            admin.setRoles(new HashSet<>(Collections.singletonList(adminRole)));
            userRepository.save(admin);

            // Create non-admin user for testing
            User user = new User();
            user.setUsername("NotAdmin");
            user.setFirstName("Not");
            user.setLastName("Sure");
            user.setPassword(passwordEncoder.encode("*!T$*$6M5722%e"));
            user.setEmail("siriswolf@gmail.com");
            user.setRoles(new HashSet<>(Collections.singletonList(userRole)));
            userRepository.save(user);
        }
        if(surgeonRepository.count() == 0){
            // Create a surgeons for testing of the app
            Surgeon surgeon = new Surgeon();
            surgeon.setFirstName("John");
            surgeon.setLastName("Doe");
            surgeon.setSpecialty("General Surgery");
            surgeonRepository.save(surgeon);

            Surgeon surgeon2 = new Surgeon();
            surgeon2.setFirstName("Jane");
            surgeon2.setLastName("Smith");
            surgeon2.setSpecialty("Orthopedic Surgery");
            surgeonRepository.save(surgeon2);

            Surgeon surgeon3 = new Surgeon();
            surgeon3.setFirstName("James");
            surgeon3.setLastName("Johnson");
            surgeon3.setSpecialty("Cardiothoracic Surgery");
            surgeonRepository.save(surgeon3);

            Surgeon surgeon4 = new Surgeon();
            surgeon4.setFirstName("Jill");
            surgeon4.setLastName("Jackson");
            surgeon4.setSpecialty("Plastic Surgery");
            surgeonRepository.save(surgeon4);

            Surgeon surgeon5 = new Surgeon();
            surgeon5.setFirstName("Jack");
            surgeon5.setLastName("Jenkins");
            surgeon5.setSpecialty("General Surgery");
            surgeon5.setCreatedBy(userRepository.findById(2L).get());
            surgeonRepository.save(surgeon5);

            Surgeon surgeon6 = new Surgeon();
            surgeon6.setFirstName("Maria");
            surgeon6.setLastName("Garcia");
            surgeon6.setSpecialty("Neurosurgery");
            surgeon6.setCreatedBy(userRepository.findById(2L).get()); // Set NotAdmin as creator
            surgeonRepository.save(surgeon6);

            Surgeon surgeon7 = new Surgeon();
            surgeon7.setFirstName("Robert");
            surgeon7.setLastName("Chen");
            surgeon7.setSpecialty("Vascular Surgery");
            surgeon7.setCreatedBy(userRepository.findById(2L).get()); // Set NotAdmin as creator
            surgeonRepository.save(surgeon7);

        }
        if (preferenceCardRepository.count() == 0){
            // Create a preference card for testing of the app
            PreferenceCard preferenceCard = new PreferenceCard();
            preferenceCard.setSurgeon(surgeonRepository.findById(1L).get());
            preferenceCard.setCreatedBy(userRepository.findById(1L).get());
            preferenceCard.setProcedureName("Appendectomy");
            preferenceCard.setInstruments("Scalpel");
            preferenceCard.setGloveSize("7.5");
            preferenceCard.setSutures("General Surgery setup");
            preferenceCard.setNotes("Patient is allergic to latex");
            preferenceCardRepository.save(preferenceCard);

            PreferenceCard preferenceCard2 = new PreferenceCard();
            preferenceCard2.setSurgeon(surgeonRepository.findById(2L).get());
            preferenceCard2.setCreatedBy(userRepository.findById(1L).get());
            preferenceCard2.setProcedureName("Knee Replacement");
            preferenceCard2.setInstruments("Saw");
            preferenceCard2.setGloveSize("8.0");
            preferenceCard2.setSutures("Orthopedic Surgery setup");
            preferenceCard.setNotes("Patient is allergic to latex");
            preferenceCardRepository.save(preferenceCard2);

            PreferenceCard preferenceCard3 = new PreferenceCard();
            preferenceCard3.setSurgeon(surgeonRepository.findById(3L).get());
            preferenceCard3.setCreatedBy(userRepository.findById(1L).get());
            preferenceCard3.setProcedureName("Heart Bypass");
            preferenceCard3.setInstruments("Heart Lung Machine");
            preferenceCard3.setGloveSize("6.5");
            preferenceCard3.setSutures("Cardiothoracic Surgery setup");
            preferenceCard.setNotes("Patient is allergic to latex");
            preferenceCardRepository.save(preferenceCard3);

            PreferenceCard preferenceCard4 = new PreferenceCard();
            preferenceCard4.setSurgeon(surgeonRepository.findById(4L).get());
            preferenceCard4.setCreatedBy(userRepository.findById(1L).get());
            preferenceCard4.setProcedureName("Breast Augmentation");
            preferenceCard4.setInstruments("Laser");
            preferenceCard4.setGloveSize("9.0");
            preferenceCard4.setSutures("Plastic Surgery setup");
            preferenceCard.setNotes("Patient is allergic to latex");
            preferenceCardRepository.save(preferenceCard4);

            PreferenceCard preferenceCard5 = new PreferenceCard();
            preferenceCard5.setSurgeon(surgeonRepository.findById(5L).get());
            preferenceCard5.setCreatedBy(userRepository.findById(2L).get());
            preferenceCard5.setProcedureName("Appendectomy");
            preferenceCard5.setInstruments("Scalpel");
            preferenceCard5.setGloveSize("7.5");
            preferenceCard5.setSutures("General Surgery setup");
            preferenceCard.setNotes("Patient is allergic to latex");
            preferenceCardRepository.save(preferenceCard5);

            PreferenceCard preferenceCard6 = new PreferenceCard();
            preferenceCard6.setSurgeon(surgeonRepository.findById(6L).get());
            preferenceCard6.setCreatedBy(userRepository.findById(2L).get());
            preferenceCard6.setProcedureName("Brain Tumor Resection");
            preferenceCard6.setInstruments("Microscope, Micro-instruments");
            preferenceCard6.setGloveSize("7.0");
            preferenceCard6.setSutures("Neurosurgery setup");
            preferenceCard6.setNotes("Patient is allergic to latex");
            preferenceCardRepository.save(preferenceCard6);

            PreferenceCard preferenceCard7 = new PreferenceCard();
            preferenceCard7.setSurgeon(surgeonRepository.findById(7L).get());
            preferenceCard7.setCreatedBy(userRepository.findById(2L).get());
            preferenceCard7.setProcedureName("Carotid Endarterectomy");
            preferenceCard7.setInstruments("Vascular clamps, Doppler");
            preferenceCard7.setGloveSize("8.5");
            preferenceCard7.setSutures("Vascular Surgery setup");
            preferenceCard7.setNotes("Patient is allergic to latex");
            preferenceCardRepository.save(preferenceCard7);
        }
    }
}
