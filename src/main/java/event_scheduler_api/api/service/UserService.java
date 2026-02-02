package event_scheduler_api.api.service;

import event_scheduler_api.api.dto.response.UserResponse;
import event_scheduler_api.api.model.User;
import event_scheduler_api.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public void deleteUser(String id) {
        this.userRepository.deleteByUserId(id);
    }
}
