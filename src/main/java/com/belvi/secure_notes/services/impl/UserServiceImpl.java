package com.belvi.secure_notes.services.impl;

import com.belvi.secure_notes.dtos.UserDTO;
import com.belvi.secure_notes.models.AppRole;
import com.belvi.secure_notes.models.Role;
import com.belvi.secure_notes.models.User;
import com.belvi.secure_notes.repositories.RoleRepository;
import com.belvi.secure_notes.repositories.UserRepository;
import com.belvi.secure_notes.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Override
    public void updateUserRole(Long userId, String roleName) {
        // Fetch the user from the repository using the provided userId.
        // If the user is not found, throw a RuntimeException with the message "User not found".
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Convert the roleName string into an AppRole enum value.
        AppRole appRole = AppRole.valueOf(roleName);

        // Fetch the role from the repository using the AppRole enum value.
        // If the role is not found, throw a RuntimeException with the message "Role not found".
        Role role = roleRepository.findByRoleName(appRole)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        // Update the user's role to the fetched role.
        user.setRole(role);

        // Save the updated user back to the repository to persist the changes.
        userRepository.save(user);
    }

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    @Override
    public UserDTO getUserById(Long id) {
        // Retrieve the User entity from the repository by ID. If the user is not found, throw an exception.
        User user = userRepository.findById(id).orElseThrow();

        // Convert the retrieved User entity to a UserDTO object and return it.
        return convertToDto(user);
    }

    /**
     * Converts a User entity to a UserDTO object.
     *
     * @param user The User entity to be converted.
     * @return A UserDTO object containing the data from the User entity.
     */
    private UserDTO convertToDto(User user) {
        return new UserDTO(
                user.getUserId(),                   // User ID
                user.getUserName(),                 // Username
                user.getEmail(),                    // Email address
                user.isAccountNonLocked(),          // Whether the account is non-locked
                user.isAccountNonExpired(),         // Whether the account is non-expired
                user.isCredentialsNonExpired(),     // Whether the credentials are non-expired
                user.isEnabled(),                   // Whether the account is enabled
                user.getCredentialsExpiryDate(),    // Credentials expiry date
                user.getAccountExpiryDate(),        // Account expiry date
                user.getTwoFactorSecret(),          // Two-factor authentication secret
                user.isTwoFactorEnabled(),          // Whether two-factor authentication is enabled
                user.getSignUpMethod(),             // Method used for signing up
                user.getRole(),                     // User role
                user.getCreatedDate(),              // Date the user was created
                user.getUpdatedDate()               // Date the user was last updated
        );
    }

    @Override
    public User findByUsername(String username) {
        Optional<User> user = userRepository.findByUserName(username);
        return user.orElseThrow(() -> new RuntimeException("User not found with username: " + username));
    }



}
