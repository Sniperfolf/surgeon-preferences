package com.iodine.surgeon_preferences.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "surgeons")
public class Surgeon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "First name is required")
    @Column(nullable = false)
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Column(nullable = false)
    private String lastName;

    private String specialty;

    @OneToMany(mappedBy = "surgeon", cascade = CascadeType.ALL)
    private List<PreferenceCard> preferenceCards;

    public Long getId() {
        return this.id;
    }

    public @NotBlank(message = "First name is required") String getFirstName() {
        return this.firstName;
    }

    public @NotBlank(message = "Last name is required") String getLastName() {
        return this.lastName;
    }

    public String getSpecialty() {
        return this.specialty;
    }

    public List<PreferenceCard> getPreferenceCards() {
        return this.preferenceCards;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstName(@NotBlank(message = "First name is required") String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(@NotBlank(message = "Last name is required") String lastName) {
        this.lastName = lastName;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public void setPreferenceCards(List<PreferenceCard> preferenceCards) {
        this.preferenceCards = preferenceCards;
    }
}
