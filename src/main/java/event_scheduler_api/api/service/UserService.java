package event_scheduler_api.api.service;

import event_scheduler_api.api.dto.response.UserResponse;
import event_scheduler_api.api.mapper.UserMapper;
import event_scheduler_api.api.model.User;
import event_scheduler_api.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public User getUserById(String id) throws Exception {
        return this.userRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new Exception("User with id " + id + " not found."));
    }

    public User getUserByEmail(String email) throws Exception {
        return this.userRepository.findByEmail(email)
                .orElseThrow(() -> new Exception("User with email " + email + " not found."));
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
                        user -> this.userMapper.toUserResponse(user))
                .collect(Collectors.toList());
    }


    public void deleteUser() throws Exception {
        User user = this.getCurrentUser();

        this.userRepository.deleteByEmail(user.getEmail());
    }

    public UserResponse getUser() throws Exception {
        User user = this.getCurrentUser();

        return this.userMapper.toUserResponse(user);
    }


}
