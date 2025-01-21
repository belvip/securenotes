package com.belvi.secure_notes.controller;

import com.belvi.secure_notes.models.Note;
import com.belvi.secure_notes.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class NoteController {
    // Injecting the NoteServices to handle business logic related to notes
    @Autowired
    private NoteService noteService;

    // Handles HTTP POST requests to create a new note
    @PostMapping
    public Note createNote(
            // The note content is passed in the request body
            @RequestBody String content,

            // Retrieves the currently authenticated user's details
            @AuthenticationPrincipal UserDetails userDetails) {

        // Extract the username of the authenticated user
        String username = userDetails.getUsername();

        // Log the username for debugging purposes
        System.out.println("USER DETAILS: " + username);

        // Delegate the note creation to the noteService and return the created note
        return noteService.createNoteForUser(username, content);
    }

    // Handles HTTP GET requests to retrieve notes for the authenticated user
    @GetMapping
    public List<Note> getUserNotes(@AuthenticationPrincipal UserDetails userDetails) {
        // Extract the username of the currently authenticated user
        String username = userDetails.getUsername();

        // Log the username to the console (for debugging purposes)
        System.out.println("USER DETAILS: " + username);

        // Retrieve and return the list of notes for the user
        return noteService.getNotesForUser(username);
    }

    // Endpoint to update an existing note
    @PutMapping("/{noteId}")
    public Note updateNote(@PathVariable Long noteId, // Path variable for note ID
                           @RequestBody String content, // Content of the note to update
                           @AuthenticationPrincipal UserDetails userDetails) { // Authentication details of the logged-in user

        // Extract the username of the currently authenticated user
        String username = userDetails.getUsername();

        // Call the service layer to update the note with the provided ID, content, and username
        return noteService.updateNoteForUser(noteId, content, username);
    }

    // Handle DELETE request to delete a note by its ID
    @DeleteMapping("/{noteId}")
    public void deleteNote(@PathVariable Long noteId,
                           @AuthenticationPrincipal UserDetails userDetails) {
        // Retrieve the username of the currently authenticated user
        String username = userDetails.getUsername();

        // Call the service to delete the note for the authenticated user
        noteService.deleteNoteForUser(noteId, username);
    }





}
