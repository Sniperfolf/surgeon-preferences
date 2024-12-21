package com.iodine.surgeon_preferences.model;

import jakarta.persistence.*;
import lombok.Data;
//This contains all of my encapsulated data for the roles table in the database I have 4 models that are used in the same way currently. I still plan adding hospitals and groups at a much later date. This is my pet project that I have wanted to make just for this program.
@Data
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;  // "ROLE_ADMIN", "ROLE_USER"

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}