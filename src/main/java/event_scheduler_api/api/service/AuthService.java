package event_scheduler_api.api.service;

import event_scheduler_api.api.dto.request.LoginRequest;
import event_scheduler_api.api.dto.request.SignUpRequest;
import event_scheduler_api.api.dto.response.UserResponse;
import event_scheduler_api.api.model.User;
import event_scheduler_api.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    public UserResponse createUser(SignUpRequest request) throws Exception {
        if (this.userRepository.existsByEmail(request.getEmail())) {
            throw new Exception("Account with email already exists!");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(this.passwordEncoder.encode((request.getPassword())));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setTimeJoined(Instant.now());

        this.userRepository.save(user);

        return UserResponse.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .hostingEvents(user.getHostingEvents())
                .participatingEvents(user.getParticipatingEvents())
                .build();
    }

    public UserResponse loginUser(LoginRequest request) throws Exception {
        this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = this.userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new Exception("User with email not found!"));

        return UserResponse.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .hostingEvents(user.getHostingEvents())
                .participatingEvents(user.getParticipatingEvents())
                .build();
    }
}
