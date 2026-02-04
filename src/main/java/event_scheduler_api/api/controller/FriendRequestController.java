package event_scheduler_api.api.controller;

import event_scheduler_api.api.dto.request.CreateFriendRequestRequest;
import event_scheduler_api.api.model.FriendRequest;
import event_scheduler_api.api.service.FriendRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/friend-requests")
public class FriendRequestController {
    @Autowired
    private FriendRequestService friendRequestService;


    @PostMapping("/")
    public ResponseEntity<?> createFriendRequest(@RequestBody CreateFriendRequestRequest request) {
        try {
            FriendRequest friendRequest = this.friendRequestService.createFriendRequest(request);
            return ResponseEntity.status(HttpStatus.OK).body(friendRequest);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        }
    }

    /*
    @GetMapping("/received")
    public ResponseEntity<?> fetchReceivedRequests() {

    }

    @GetMapping("/sent")
    public ResponseEntity<?> fetchSentRequests() {

    }

    @PostMapping("/{id}/accept")
    public ResponseEntity<?> acceptRequest() {

    }

    @PostMapping("/{id}/reject")
    public ResponseEntity<?> rejectRequest() {

    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteSentRequest() {

    }
*/
}
