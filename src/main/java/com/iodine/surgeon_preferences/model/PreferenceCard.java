package com.iodine.surgeon_preferences.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data

@Entity
@Table(name = "preference_cards")
public class PreferenceCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String procedureName;

    private String gloveSize;

    @Column(length = 1000)
    private String instruments;

    @Column(length = 1000)
    private String sutures;

    @Column(length = 5000)
    private String notes;

    private LocalDateTime lastUpdated;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "surgeon_id")
    private Surgeon surgeon;

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User createdBy;

    @PrePersist
    @PreUpdate
    public void updateTimestamp() {
        lastUpdated = LocalDateTime.now();
    }

    public Long getId() {
        return this.id;
    }

    public String getProcedureName() {
        return this.procedureName;
    }

    public String getGloveSize() {
        return this.gloveSize;
    }

    public String getInstruments() {
        return this.instruments;
    }

    public String getSutures() {
        return this.sutures;
    }

    public String getNotes() {
        return this.notes;
    }

    public LocalDateTime getLastUpdated() {
        return this.lastUpdated;
    }

    public Surgeon getSurgeon() {
        return this.surgeon;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
    }

    public void setGloveSize(String gloveSize) {
        this.gloveSize = gloveSize;
    }

    public void setInstruments(String instruments) {
        this.instruments = instruments;
    }

    public void setSutures(String sutures) {
        this.sutures = sutures;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public void setSurgeon(Surgeon surgeon) {
        this.surgeon = surgeon;
    }
}
