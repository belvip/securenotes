package com.belvi.secure_notes.repositories;


import com.belvi.secure_notes.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String username);

    Boolean existsByUserName(String username);

    Boolean existsByEmail(String email);

//   Boolean existsByEmail(@NotBlank @Size(max = 50) @Email String email);
}

