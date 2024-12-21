package com.iodine.surgeon_preferences.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PreferenceCardTest {

    private PreferenceCard preferenceCard;
    private Surgeon surgeon;
    private User user;

    @BeforeEach
    void setUp() {
        preferenceCard = new PreferenceCard();
        surgeon = new Surgeon();
        user = new User();
    }

    @Test
    void testBasicFields() {

        preferenceCard.setId(1L);
        preferenceCard.setProcedureName("Heart Surgery");
        preferenceCard.setGloveSize("7.5");
        preferenceCard.setInstruments("Scalpel, Forceps");
        preferenceCard.setSutures("Silk 3-0");
        preferenceCard.setNotes("Patient prep required");


        assertEquals(1L, preferenceCard.getId());
        assertEquals("Heart Surgery", preferenceCard.getProcedureName());
        assertEquals("7.5", preferenceCard.getGloveSize());
        assertEquals("Scalpel, Forceps", preferenceCard.getInstruments());
        assertEquals("Silk 3-0", preferenceCard.getSutures());
        assertEquals("Patient prep required", preferenceCard.getNotes());
    }

    @Test
    void testTimestampUpdate() {
        LocalDateTime before = LocalDateTime.now();
        preferenceCard.updateTimestamp();
        LocalDateTime after = LocalDateTime.now();

        assertNotNull(preferenceCard.getLastUpdated());
        assertTrue(preferenceCard.getLastUpdated().isAfter(before) ||
                preferenceCard.getLastUpdated().isEqual(before));
        assertTrue(preferenceCard.getLastUpdated().isBefore(after) ||
                preferenceCard.getLastUpdated().isEqual(after));
    }

    @Test
    void testSurgeonAssociation() {
        surgeon.setId(1L);
        surgeon.setFirstName("John");
        surgeon.setLastName("Doe");

        preferenceCard.setSurgeon(surgeon);

        assertEquals(surgeon, preferenceCard.getSurgeon());
        assertEquals("John", preferenceCard.getSurgeon().getFirstName());
        assertEquals("Doe", preferenceCard.getSurgeon().getLastName());
    }

    @Test
    void testUserAssociation() {
        user.setId(1L);
        preferenceCard.setCreatedBy(user);

        assertEquals(user, preferenceCard.getCreatedBy());
        assertEquals(1L, preferenceCard.getCreatedBy().getId());
    }

    @Test
    void testLastUpdatedManualSet() {
        LocalDateTime timestamp = LocalDateTime.now();
        preferenceCard.setLastUpdated(timestamp);

        assertEquals(timestamp, preferenceCard.getLastUpdated());
    }
}