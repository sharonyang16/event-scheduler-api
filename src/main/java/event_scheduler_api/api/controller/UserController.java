package event_scheduler_api.api.controller;

import event_scheduler_api.api.dto.response.UserResponse;
import event_scheduler_api.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // TODO: Deprecate in the future, only for testing purposes
    @GetMapping("/")
    public ResponseEntity<?> fetchAllUsers() {
        try {
            List<UserResponse> users = this.userService.getAllUsers();
            return ResponseEntity.status(HttpStatus.OK).body(users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> fetchUser() {
        try {
            UserResponse user = this.userService.getUser();
            return ResponseEntity.status(HttpStatus.OK).body(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        }
    }

    @DeleteMapping("/me")
    public ResponseEntity<?> deleteUser() {
        try {
            this.userService.deleteUser();
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "User deleted."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        }
    }
}
