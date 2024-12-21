package com.iodine.surgeon_preferences.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class SurgeonTest {

    private Validator validator;
    private Surgeon surgeon;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        surgeon = new Surgeon();
    }

    @Test
    void testValidSurgeon() {

        surgeon.setFirstName("John");
        surgeon.setLastName("Doe");
        surgeon.setSpecialty("Cardiology");
        surgeon.setId(1L);
        surgeon.setPreferenceCards(new ArrayList<>());


        Set<ConstraintViolation<Surgeon>> violations = validator.validate(surgeon);
        assertTrue(violations.isEmpty());


        assertEquals("John", surgeon.getFirstName());
        assertEquals("Doe", surgeon.getLastName());
        assertEquals("Cardiology", surgeon.getSpecialty());
        assertEquals(1L, surgeon.getId());
        assertNotNull(surgeon.getPreferenceCards());
    }

    @Test
    void testFirstNameBlank() {
        surgeon.setFirstName("");
        surgeon.setLastName("Doe");
        surgeon.setSpecialty("Cardiology");

        Set<ConstraintViolation<Surgeon>> violations = validator.validate(surgeon);
        assertEquals(1, violations.size());
        assertEquals("First name is required", violations.iterator().next().getMessage());
    }

    @Test
    void testLastNameBlank() {
        surgeon.setFirstName("John");
        surgeon.setLastName("");
        surgeon.setSpecialty("Cardiology");

        Set<ConstraintViolation<Surgeon>> violations = validator.validate(surgeon);
        assertEquals(1, violations.size());
        assertEquals("Last name is required", violations.iterator().next().getMessage());
    }

    @Test
    void testSpecialtyBlank() {
        surgeon.setFirstName("John");
        surgeon.setLastName("Doe");
        surgeon.setSpecialty("");

        Set<ConstraintViolation<Surgeon>> violations = validator.validate(surgeon);
        assertEquals(1, violations.size());
        assertEquals("Specialty is required", violations.iterator().next().getMessage());
    }

    @Test
    void testUserAssociation() {
        User user = new User();
        user.setId(1L);
        surgeon.setCreatedBy(user);

        assertEquals(user, surgeon.getCreatedBy());
    }
}