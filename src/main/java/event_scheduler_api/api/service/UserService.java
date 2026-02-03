package event_scheduler_api.api.service;

import event_scheduler_api.api.dto.response.UserResponse;
import event_scheduler_api.api.model.User;
import event_scheduler_api.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<UserResponse> getAllUsers() {
        List<User> allUsers = this.userRepository.findAll();

        return allUsers.stream().map(
                        user -> UserResponse.builder()
                                .userId(user.getUserId())
                                .email(user.getEmail())
                                .firstName(user.getFirstName())
                                .lastName(user.getLastName())
                                .hostingEvents(user.getHostingEvents())
                                .participatingEvents(user.getParticipatingEvents())
                                .build())
                .collect(Collectors.toList());
    }

    public void deleteUser() throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) throw new Exception("An auth error has occurred.");
        String email = auth.getName();

        this.userRepository.deleteByEmail(email);
    }

    public UserResponse getUser() throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) throw new Exception("An auth error has occurred.");
        String email = auth.getName();

        User user = this.userRepository.findByEmail(email).orElseThrow(() -> new Exception("User not found"));

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
