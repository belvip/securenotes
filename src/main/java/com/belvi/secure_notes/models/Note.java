package com.belvi.secure_notes.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@Lob
    @Column(columnDefinition = "TEXT")
    private String content;

    private String ownerUsername;
}

//ALTER TABLE note ALTER COLUMN content TYPE jsonb USING content::jsonb;

// ALTER TABLE note ALTER COLUMN content TYPE TEXT USING content::text;

