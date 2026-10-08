package com.messmanagement.service;

import com.messmanagement.dto.AuthRequest;
import com.messmanagement.dto.AuthResponse;
import com.messmanagement.dto.RegisterRequest;
import com.messmanagement.entity.MessProviderProfile;
import com.messmanagement.entity.StudentProfile;
import com.messmanagement.entity.User;
import com.messmanagement.enums.ProviderStatus;
import com.messmanagement.enums.Role;
import com.messmanagement.repository.MessProviderProfileRepository;
import com.messmanagement.repository.StudentProfileRepository;
import com.messmanagement.repository.UserRepository;
import com.messmanagement.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final StudentProfileRepository studentProfileRepository;
    private final MessProviderProfileRepository providerProfileRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, StudentProfileRepository studentProfileRepository,
                       MessProviderProfileRepository providerProfileRepository, PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userRepository = userRepository;
        this.studentProfileRepository = studentProfileRepository;
        this.providerProfileRepository = providerProfileRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public AuthResponse register(RegisterRequest request) {
        if (request.getRole() == Role.ROLE_ADMIN) {
            throw new IllegalArgumentException("Admin signup is not allowed");
        }
        String collegeId = request.getCollegeId();
        if (collegeId != null && collegeId.isBlank()) {
            collegeId = null;
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already registered");
        }
        if (collegeId != null && userRepository.existsByCollegeId(collegeId)) {
            throw new IllegalArgumentException("College ID already registered");
        }

        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .collegeId(collegeId)
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .enabled(true)
                .banned(false)
                .build();
        User savedUser = userRepository.save(user);

        if (savedUser.getRole() == Role.ROLE_STUDENT) {
            studentProfileRepository.save(StudentProfile.builder()
                    .user(savedUser)
                    .phone(request.getPhone())
                    .area(request.getArea())
                    .walletBalance(BigDecimal.ZERO)
                    .build());
        } else if (savedUser.getRole() == Role.ROLE_PROVIDER) {
            providerProfileRepository.save(MessProviderProfile.builder()
                    .user(savedUser)
                    .phone(request.getPhone())
                    .status(ProviderStatus.PENDING)
                    .build());
        }

        String token = jwtService.generateToken(savedUser);
        return new AuthResponse(token, savedUser.getId(), savedUser.getFullName(), savedUser.getEmail(), savedUser.getRole());
    }

    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid login"));
        String token = jwtService.generateToken(user);
        return new AuthResponse(token, user.getId(), user.getFullName(), user.getEmail(), user.getRole());
    }
}
