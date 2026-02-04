package event_scheduler_api.api.controller;

import event_scheduler_api.api.dto.request.CreateFriendRequestRequest;
import event_scheduler_api.api.dto.response.ReceivedFriendRequestResponse;
import event_scheduler_api.api.dto.response.SentFriendRequestResponse;

import event_scheduler_api.api.service.FriendRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/friend-requests")
public class FriendRequestController {
    @Autowired
    private FriendRequestService friendRequestService;


    @PostMapping("/")
    public ResponseEntity<?> createFriendRequest(@RequestBody CreateFriendRequestRequest request) {
        try {
            this.friendRequestService.createFriendRequest(request);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Friend request sent."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        }
    }


    @GetMapping("/received")
    public ResponseEntity<?> fetchReceivedRequests() {
        try {
            List<ReceivedFriendRequestResponse> friendRequests = this.friendRequestService.getMyReceived();
            return ResponseEntity.status(HttpStatus.OK).body(friendRequests);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        }
    }


    @GetMapping("/sent")
    public ResponseEntity<?> fetchSentRequests() {
        try {
            List<SentFriendRequestResponse> friendRequests = this.friendRequestService.getMySent();
            return ResponseEntity.status(HttpStatus.OK).body(friendRequests);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        }
    }


    @PostMapping("/{id}/accept")
    public ResponseEntity<?> acceptRequest(@PathVariable String id) {
        try {
            this.friendRequestService.acceptFriendRequestById(id);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Friend request accepted."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        }
    }


    @PostMapping("/{id}/reject")
    public ResponseEntity<?> rejectRequest(@PathVariable String id) {
        try {
            this.friendRequestService.rejectFriendRequestById(id);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Friend request rejected."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSentRequest(@PathVariable String id) {
        try {
            this.friendRequestService.deleteFriendRequestById(id);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Friend request deleted."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        }
    }

}
