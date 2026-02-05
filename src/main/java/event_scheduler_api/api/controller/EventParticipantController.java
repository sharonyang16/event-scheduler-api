package event_scheduler_api.api.controller;

import event_scheduler_api.api.dto.response.EventInviteResponse;
import event_scheduler_api.api.model.EventParticipationStatus;
import event_scheduler_api.api.service.EventParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/event-invites")
public class EventParticipantController {
    @Autowired
    private EventParticipantService eventParticipantService;

    @GetMapping("/")
    public ResponseEntity<?> getMyInvites() {
        try {
            List< EventInviteResponse> invites = this.eventParticipantService.getMyInvites();
            return ResponseEntity.status(HttpStatus.OK).body(invites);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        }
    }

    @PostMapping("/{id}/accept")
    public ResponseEntity<?> acceptInvite(@PathVariable String id) {
        try {
            this.eventParticipantService.updateEventParticipationStatusById(id, EventParticipationStatus.ACCEPTED);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Set event participation to accepted."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        }
    }


    @PostMapping("/{id}/decline")
    public ResponseEntity<?> declineInvite(@PathVariable String id) {
        try {
            this.eventParticipantService.updateEventParticipationStatusById(id, EventParticipationStatus.DECLINED);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Set event participation to declined."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        }
    }

    @PostMapping("/{id}/maybe")
    public ResponseEntity<?> maybeInvite(@PathVariable String id) {
        try {
            this.eventParticipantService.updateEventParticipationStatusById(id, EventParticipationStatus.MAYBE);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Set event participation to maybe."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        }
    }
}
