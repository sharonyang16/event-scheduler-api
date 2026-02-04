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

    private UserResponse userToResponse(User user) {
        return UserResponse.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .hostingEvents(user.getHostingEvents())
                .participatingEvents(user.getParticipatingEvents())
                .build();
    }

    public User getUserById(String userId) throws Exception {
        return this.userRepository.findByUserId(userId).orElseThrow(() -> new Exception("User not found"));
    }

    public User getUserByEmail(String email) throws Exception {
        return this.userRepository.findByEmail(email).orElseThrow(() -> new Exception("User not found!"));
    }

    public User getCurrentUser() throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) throw new Exception("An error has occurred.");
        String email = auth.getName();

        return this.getUserByEmail(email);
    }

    public List<UserResponse> getAllUsers() {
        List<User> allUsers = this.userRepository.findAll();

        return allUsers.stream().map(
                        this::userToResponse)
                .collect(Collectors.toList());
    }


    public void deleteUser() throws Exception {
        User user = this.getCurrentUser();

        this.userRepository.deleteByEmail(user.getEmail());
    }

    public UserResponse getUser() throws Exception {
        User user = this.getCurrentUser();

        return this.userToResponse(user);
    }


}
