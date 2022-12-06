package com.wildtac.service;

import com.wildtac.domain.user.User;
import com.wildtac.repository.UserRepo;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;

@Service
@Slf4j
@AllArgsConstructor
public class UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    // TODO: 06.12.2022 set logs

    /**
     * Method creates new user.
     * Checks for unique email or phone (depends on which registration way was selected by user)
     * @param user new user
     * @return User user
     * @throws EntityExistsException - if user with the same phone number or email already exists
     * @throws RuntimeException - if there are no claims for registration
     */
    public User createNewUser(User user) {
        if (user.getEmail() != null) {
            if (userRepo.existsByEmail(user.getEmail())) {
                String messageCause = String.format("User with email '%s' already exists", user.getEmail());
                throw new EntityExistsException(String.format("Failed to create new user with email '%s'\n" + messageCause, user.getEmail()));
            }
        } else if (user.getPhoneNumber() != null) {
            if (userRepo.existsByPhoneNumber(user.getPhoneNumber())) {
                String messageCause = String.format("User with phone number '%s' already exists", user.getPhoneNumber());
                throw new EntityExistsException(String.format("Failed to create new user with phone number '%s'\n" + messageCause, user.getPhoneNumber()));
            }
        } else {
            String messageCause = String.format("There are no claims");
            throw new RuntimeException("Failed to create new user" + messageCause);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = userRepo.save(user);
        log.info(String.format("User %s was saved", user));
        return savedUser;
    }
}
