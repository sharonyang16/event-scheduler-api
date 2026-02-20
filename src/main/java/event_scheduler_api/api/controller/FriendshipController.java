package event_scheduler_api.api.controller;

import event_scheduler_api.api.dto.response.FriendshipResponse;
import event_scheduler_api.api.service.FriendshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/friends")
@RequiredArgsConstructor
public class FriendshipController {
    private final FriendshipService friendshipService;

    @GetMapping("/")
    public ResponseEntity<?> fetchMyFriends() {
        try {
            List<FriendshipResponse> friends = this.friendshipService.getMyFriends();
            return ResponseEntity.status(HttpStatus.OK).body(friends);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping("/check/{userId}")
    public ResponseEntity<?> checkFriendship(@PathVariable String userId) {
        try {
            boolean areFriends = this.friendshipService.checkFriendship(userId);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("areFriends", areFriends));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeFriendship(@PathVariable String id) {
        try {
            this.friendshipService.deleteFriendshipById(id);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Friend removed."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        }
    }


}
