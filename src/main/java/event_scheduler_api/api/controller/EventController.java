package event_scheduler_api.api.controller;

import event_scheduler_api.api.dto.request.CreateEventRequest;
import event_scheduler_api.api.dto.response.EventResponse;
import event_scheduler_api.api.service.EventService;
import event_scheduler_api.api.dto.request.EventRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/events")
public class EventController {
    @Autowired
    private EventService eventService;

    @GetMapping("/")
    public ResponseEntity<?> fetchAllEvents() {
        try {
            List<EventResponse> event = this.eventService.getAllEvents();
            return ResponseEntity.status(HttpStatus.OK).body(event);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> createEvent(@RequestBody CreateEventRequest request) {
        try {
            EventResponse event = this.eventService.addEvent(request);
            return ResponseEntity.status(HttpStatus.OK).body(event);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<?> fetchEvent(@PathVariable String eventId) {
        try {
            EventResponse event = this.eventService.getEvent(eventId);
            return ResponseEntity.status(HttpStatus.OK).body(event);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        }
    }

    @PatchMapping("/{eventId}")
    public ResponseEntity<?> updateEvent(@PathVariable String eventId, @RequestBody EventRequest request) {
        try {
            EventResponse event = this.eventService.partialUpdate(eventId, request);
            return ResponseEntity.status(HttpStatus.OK).body(event);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        }
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<?> removeEvent(@PathVariable String eventId) {
        try {
            this.eventService.deleteEvent(eventId);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Event deleted."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        }
    }

}
