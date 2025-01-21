package com.belvi.secure_notes.services.impl;

import com.belvi.secure_notes.models.Note;
import com.belvi.secure_notes.repositories.NoteRepository;
import com.belvi.secure_notes.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {

    // Injecting the NoteRepository to interact with the database
    @Autowired
    private NoteRepository noteRepository;

    // Creates a new note for a specified user
    @Override
    public Note createNoteForUser(String username, String content) {
        // Create a new Note instance
        Note note = new Note();

        // Set the content and owner's username for the note
        note.setContent(content);
        note.setOwnerUsername(username);

        // Save the note in the repository and return the saved instance
        Note savedNote = noteRepository.save(note);
        return savedNote;
    }

    // Update note
    @Override
    public Note updateNoteForUser(Long noteId, String content, String username) {
        // Retrieve the note by its ID or throw an exception if not found
        Note note = noteRepository.findById(noteId).orElseThrow(() ->
                new RuntimeException("Note not found"));

        // Update the content of the retrieved note
        note.setContent(content);

        Note updatedNote = noteRepository.save(note);

        // Save the updated note back to the repository and return it
        return updatedNote;
    }

    // Deletes a specific note for a given user
    @Override
    public void deleteNoteForUser(Long noteId, String username) {
        // Delete the note from the repository using its ID
        noteRepository.deleteById(noteId);
    }

    // Retrieves all notes for a specified user
    @Override
    public List<Note> getNotesForUser(String username) {
        // Fetch the list of notes owned by the specified username
        List<Note> personalNotes = noteRepository
                .findByOwnerUsername(username);

        // Return the list of notes
        return personalNotes;
    }

}
