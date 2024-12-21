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
//Good ol validation
    @NotBlank(message = "First name is required")
    @Column(nullable = false)
    private String firstName;
//Some more validation
    @NotBlank(message = "Last name is required")
    @Column(nullable = false)
    private String lastName;
//And some more its been such a long day. I hope these get read. Im So tired.
    @NotBlank(message = "Specialty is required")
    @Column(nullable = false)
    private String specialty;

    @OneToMany(mappedBy = "surgeon", cascade = CascadeType.ALL)
    private List<PreferenceCard> preferenceCards;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User createdBy;

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

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
