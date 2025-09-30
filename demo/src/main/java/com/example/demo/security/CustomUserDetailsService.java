package com.example.demo.security;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Fetch the user from the database using the email
        User user = userRepository.findByEmail(email);

        // If no user is found, throw an exception
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        // Return CustomUserDetails with user
        return new CustomUserDetails(user);  // Just pass the user object
    }
}
