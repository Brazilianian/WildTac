package com.wildtac.service.security;

import com.wildtac.domain.user.User;
import com.wildtac.repository.UserRepo;
import com.wildtac.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class UserSecurityService implements UserDetailsService {

    private final UserRepo userRepo;
    private final UserService userService;

    /**
     * The fundamental method of spring security that gives opportunity to load users
     * The method uses two different ways of finding users - by email or phoneNumber, where email and phoneNumber are fields of user
     * If method matches the username with email, it will try to find user by his email
     * Else it will try to find user by his phone number
     * @param username - email or phoneNumber of user
     * @return - founded user
     * @throws UsernameNotFoundException - in case of not founded user
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUserByEmailOrPhoneNumber(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User with phone number '%s' was not found", username));
        }
        return user;
    }
}
