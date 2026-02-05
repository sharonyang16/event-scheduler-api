package event_scheduler_api.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/event-invites")
public class EventParticipantController {

    @GetMapping("/")
    public ResponseEntity<?> getMyInvites() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Invites"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        }
    }

    @PostMapping("/{id}/accept")
    public ResponseEntity<?> acceptInvite(@PathVariable String id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Set event participation to accepted."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        }
    }


    @PostMapping("/{id}/decline")
    public ResponseEntity<?> declineInvite(@PathVariable String id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Set event participation to declined."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        }
    }

    @PostMapping("/{id}/maybe")
    public ResponseEntity<?> maybeInvite(@PathVariable String id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Set event participation to maybe."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        }
    }
}
