package event_scheduler_api.api.controller;

import event_scheduler_api.api.service.EventService;
import event_scheduler_api.api.model.Event;
import event_scheduler_api.api.dto.request.EventRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {
    @Autowired
    private EventService eventService;

    @GetMapping("/")
    public ResponseEntity<?> fetchAllEvents() {

        try {
            List<Event> event = eventService.getAllEvents();
            return ResponseEntity.status(HttpStatus.OK).body(event);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> createEvent(@RequestBody EventRequest request) {
        try {
            Event event = eventService.addEvent(request);
            return ResponseEntity.status(HttpStatus.OK).body(event);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<?> fetchEvent(@PathVariable String eventId) {
        try {
            Event event = eventService.getEvent(eventId);
            return ResponseEntity.status(HttpStatus.OK).body(event);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PatchMapping("/{eventId}")
    public ResponseEntity<?> updateEvent(@PathVariable String eventId, @RequestBody EventRequest request) {
        try {
            Event event = eventService.partialUpdate(eventId, request);
            return ResponseEntity.status(HttpStatus.OK).body(event);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // TODO: @DeleteMapping("/{eventId})


}
