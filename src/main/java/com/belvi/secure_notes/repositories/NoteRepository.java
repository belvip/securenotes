package com.belvi.secure_notes.repositories;

import com.belvi.secure_notes.models.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Repository interface for performing database operations on Note entities
public interface NoteRepository extends JpaRepository<Note, Long> {

    // Custom query method to retrieve a list of notes by the owner's username
    List<Note> findByOwnerUsername(String ownerUsername);
}
