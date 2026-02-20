package event_scheduler_api.api.service;

import event_scheduler_api.api.dto.request.LoginRequest;
import event_scheduler_api.api.dto.request.SignUpRequest;
import event_scheduler_api.api.dto.response.AuthResponse;
import event_scheduler_api.api.model.User;
import event_scheduler_api.api.repository.UserRepository;
import event_scheduler_api.api.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthResponse createUser(SignUpRequest request) throws Exception {
        if (this.userRepository.existsByEmail(request.getEmail())) {
            throw new Exception("Account with email " + request.getEmail() + " already exists!");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(this.passwordEncoder.encode((request.getPassword())));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        this.userRepository.save(user);

        String token = this.jwtUtil.generateToken(request.getEmail());

        return AuthResponse.builder().token(token).email(user.getEmail()).build();
    }

    public AuthResponse loginUser(LoginRequest request) throws Exception {
        this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = this.userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new Exception("User with email not found!"));

        String token = this.jwtUtil.generateToken(request.getEmail());

        return AuthResponse.builder().token(token).email(user.getEmail()).build();
    }
}
