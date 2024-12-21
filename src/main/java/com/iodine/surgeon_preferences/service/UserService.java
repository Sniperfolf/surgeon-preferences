package com.iodine.surgeon_preferences.service;

import com.iodine.surgeon_preferences.dto.RegistrationDto;
import com.iodine.surgeon_preferences.model.User;
import com.iodine.surgeon_preferences.model.Role;
import com.iodine.surgeon_preferences.repository.RoleRepository;
import com.iodine.surgeon_preferences.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(RegistrationDto registration) {
        if (userRepository.findByUsername(registration.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setUsername(registration.getUsername());
        user.setPassword(passwordEncoder.encode(registration.getPassword()));
        user.setEmail(registration.getEmail());
        user.setFirstName(registration.getFirstName());
        user.setLastName(registration.getLastName());

        return userRepository.save(user);
    }
    public User save(User user) {
        return userRepository.save(user);
    }
    public void makeUserAdmin(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                .orElseThrow(() -> new RuntimeException("Admin role not found"));

        user.getRoles().add(adminRole);
        userRepository.save(user);
    }

    public void removeAdminRole(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.getRoles().removeIf(role -> role.getName().equals("ROLE_ADMIN"));
        userRepository.save(user);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
    }
    public void deleteUser(Long userId) {
        User user = getUserById(userId);

        // Remove user from all roles and delete their associated data TODO: need to add logic here before live actual for cards and surgeons. currently this is fine for testing and turnin.

        userRepository.delete(user);
    }
    public List<User> searchUsers(String searchTerm) {
        return userRepository.findByUsernameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
                searchTerm, searchTerm, searchTerm, searchTerm
        );
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("No authenticated user found");
        }

        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
    }


    public User registerNewUser(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }


        user.setPassword(passwordEncoder.encode(user.getPassword()));


        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Default role not found"));
        user.setRoles(new HashSet<>(Collections.singletonList(userRole)));

        return userRepository.save(user);
    }

}
