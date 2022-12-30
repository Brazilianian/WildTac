package com.wildtac.service;

import com.wildtac.domain.user.User;
import com.wildtac.exception.alreadyexists.UserAlreadyExistsException;
import com.wildtac.repository.UserRepo;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

import static com.wildtac.domain.user.security.UserRole.USER;

@Service
@Slf4j
@AllArgsConstructor
public class UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    /**
     * Method creates new user.
     * Checks for unique email or phone (depends on which registration way was selected by user)
     *
     * @param user new user
     * @return User user
     * @throws UserAlreadyExistsException - if user with the same phone number or email already exists
     * @throws RuntimeException           - if there are no claims for registration
     */
    public User createNewUser(User user) {
        if (user.getEmail() != null) {
            if (userRepo.existsByEmail(user.getEmail())) {
                String messageCause = String.format("User with email '%s' already exists", user.getEmail());
                throw new UserAlreadyExistsException(String.format("Failed to create new user with email '%s'\n" + messageCause, user.getEmail()));
            }
        } else if (user.getPhoneNumber() != null) {
            if (userRepo.existsByPhoneNumber(user.getPhoneNumber())) {
                String messageCause = String.format("User with phone number '%s' already exists", user.getPhoneNumber());
                throw new UserAlreadyExistsException(String.format("Failed to create new user with phone number '%s'\n" + messageCause, user.getPhoneNumber()));
            }
        } else {
            String messageCause = String.format("%nThere are no claims");
            throw new RuntimeException("Failed to create new user" + messageCause);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(USER);

        User savedUser = userRepo.save(user);

        log.info(String.format("User '%s' was saved", user));
        return savedUser;
    }

    /**
     * The method uses two different ways of finding users - by email or phoneNumber, where email and phoneNumber are fields of user
     * If method matches the username with email, it will try to find user by his email
     * Else it will try to find user by his phone number
     * @param claims - may contain email or phoneNumber
     * @return - founded user
     */
    public User getUserByEmailOrPhoneNumber(String claims) {
        boolean isEmail = Pattern.matches("^(.+)@(.+)$", claims);
        if (isEmail) {
            return userRepo.findByEmail(claims)
                    .orElse(null);
        } else {
            return userRepo.findByPhoneNumber(claims)
                    .orElse(null);
        }
    }

    public User save(User user) {
        return userRepo.save(user);
    }


}
