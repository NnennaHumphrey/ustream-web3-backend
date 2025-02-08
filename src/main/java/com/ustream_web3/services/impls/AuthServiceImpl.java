package com.ustream_web3.services.impls;

import com.ustream_web3.dtos.LoginDTO;
import com.ustream_web3.dtos.RegistrationDTO;
import com.ustream_web3.entities.Role;
import com.ustream_web3.entities.User;
import com.ustream_web3.exceptions.*;
import com.ustream_web3.mappers.RegistrationMapper;
import com.ustream_web3.repositories.UserRepository;
import com.ustream_web3.services.AuthService;
import com.ustream_web3.services.EmailService;
import com.ustream_web3.services.OtpService;
import com.ustream_web3.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AuthServiceImpl implements AuthService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private OtpService otpService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private CustomUsersDetailsService customUsersDetailsService;

    @Autowired
    private RegistrationMapper registrationMapper;


    private final Set<String> loggedOutTokens = ConcurrentHashMap.newKeySet();

    @Override
    public void registerUser(RegistrationDTO registrationDTO) {
        try {
            if (registrationDTO == null || registrationDTO.getUsername() == null || registrationDTO.getEmail() == null) {
                throw new InvalidCredentialsException("Invalid registration data: username and email must not be null.");
            }

            if (userRepository.findByUsername(registrationDTO.getUsername()).isPresent()) {
                throw new UserAlreadyExistException("Username already exists: " + registrationDTO.getUsername());
            }

            if (userRepository.findByEmail(registrationDTO.getEmail()).isPresent()) {
                throw new UserAlreadyExistException("Email already exists: " + registrationDTO.getEmail());
            }

            User user = registrationMapper.toEntity(registrationDTO);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRole(Role.USER);
            user.setEnabled(true);

            userRepository.save(user);

            try {
                otpService.generateAndSaveOtp(user);
            } catch (Exception e) {
                throw new OtpGenerationException("Failed to generate OTP for user: " + registrationDTO.getUsername(), e);
            }

        } catch (InvalidCredentialsException | UserAlreadyExistException | OtpGenerationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException("Unexpected error during user registration: " + ex.getMessage(), ex);
        }
    }


    @Override
    public String loginUser(LoginDTO loginDTO) {
        User user = userRepository.findByUsername(loginDTO.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (!user.isVerified()) {
            throw new OtpExpiredException("User is not verified. Please complete OTP verification.");
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getUsername(),
                        loginDTO.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = customUsersDetailsService.loadUserByUsername(loginDTO.getUsername());
        return jwtUtils.generateToken(userDetails.getUsername());
    }

    @Override
    public void logout() {
        String token = getCurrentToken();
        if (token != null) {
            loggedOutTokens.add(token);
        }

    }

    private String getCurrentToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getCredentials() instanceof String) {
            return (String) authentication.getCredentials();
        }
        return null;
    }

    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return authentication.getName();
        }
        return null;
    }

    /**
     * Deletes users who are not verified and whose OTP has expired.
     */
    private void deleteExpiredUnverifiedUsers() {
        List<User> unverifiedUsers = userRepository.findAllByEnabledFalse();

        for (User user : unverifiedUsers) {
            if (otpService.isOtpExpired(user)) {
                userRepository.delete(user);
            }
        }
    }
}
