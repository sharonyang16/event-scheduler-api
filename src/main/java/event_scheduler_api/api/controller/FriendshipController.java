package event_scheduler_api.api.controller;

import event_scheduler_api.api.dto.response.UserSummaryResponse;
import event_scheduler_api.api.service.FriendshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/friends")
public class FriendshipController {

    @Autowired
    private FriendshipService friendshipService;
    @GetMapping("/")
    public ResponseEntity<?> fetchMyFriends() {
        try {
            List<UserSummaryResponse> friends = this.friendshipService.getMyFriends();
            return ResponseEntity.status(HttpStatus.OK).body(friends);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        }
    }


}
