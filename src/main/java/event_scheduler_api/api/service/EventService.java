package event_scheduler_api.api.service;

import event_scheduler_api.api.dto.request.AddParticipantsToEventRequest;
import event_scheduler_api.api.dto.request.CreateEventRequest;
import event_scheduler_api.api.dto.response.EventResponse;
import event_scheduler_api.api.dto.response.UserContactResponse;
import event_scheduler_api.api.dto.request.UpdateEventRequest;
import event_scheduler_api.api.model.Event;
import event_scheduler_api.api.model.User;
import event_scheduler_api.api.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private EventParticipantService eventParticipantService;

    public Event getEventById(String id) throws Exception {
        return this.eventRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new Exception("Event with id " + id + " not found."));
    }

    public EventResponse eventToResponse(Event event) {
        return EventResponse.builder()
                .eventId(event.getId().toString())
                .name(event.getName())
                .host(UserContactResponse.builder()
                        .email(event.getHost().getEmail())
                        .firstName(event.getHost().getFirstName())
                        .lastName(event.getHost().getLastName())
                        .build())
                .startTime(event.getStartTime().toLocalDateTime())
                .endTime(event.getEndTime().toLocalDateTime())
                .timezone(event.getStartTime().getZone().getDisplayName(TextStyle.SHORT, Locale.US))
                .participants(
                        event.getParticipants().stream().map(
                                        eventParticipant -> this.eventParticipantService.eventParticipantToResponse(eventParticipant))
                                .collect(Collectors.toList())
                )
                .createdAt(event.getCreatedAt())
                .updatedAt(event.getUpdatedAt())
                .build();
    }

    private boolean isEventNameValid(String name) {
        return name != null && !name.isEmpty();
    }

    public List<EventResponse> getAllEvents() throws Exception {
        List<Event> events = this.eventRepository.findAll();

        return events.stream().map(this::eventToResponse).toList();
    }

    public EventResponse getEvent(String id) throws Exception {
        Event event = this.getEventById(id);

        return this.eventToResponse(event);
    }

    public EventResponse addEvent(CreateEventRequest request) throws Exception {
        if (!isEventNameValid(request.getName())) {
            throw new Exception("Event name cannot be blank!");
        }
        User user = this.userService.getCurrentUser();

        Event event = new Event();
        event.setName(request.getName());
        event.setHost(user);
        event.setStartTime(ZonedDateTime.of(request.getStartTime(), ZoneId.of(request.getTimezone())));
        event.setEndTime(ZonedDateTime.of(request.getEndTime(), ZoneId.of(request.getTimezone())));

        this.eventRepository.save(event);

        request.getParticipants().forEach(
                email -> {
                    try {
                        this.eventParticipantService.createEventParticipant(email, event);
                    } catch (Exception e) {
                        // do nothing
                    }
                });

        return this.eventToResponse(event);
    }

    public EventResponse partialUpdate(String id, UpdateEventRequest request) throws Exception {
        Event event = this.getEventById(id);
        User user = this.userService.getCurrentUser();

        if (!user.equals(event.getHost())) {
            throw new Exception("Cannot update event if you're not the host.");
        }

        if (this.isEventNameValid(request.getName())) {
            event.setName(request.getName());
        }

        if (request.getTimezone() != null) {
            if (request.getStartTime() != null && request.getEndTime() != null) {
                ZonedDateTime newStart = ZonedDateTime.of(request.getStartTime(), ZoneId.of(request.getTimezone()));
                ZonedDateTime newEnd = ZonedDateTime.of(request.getEndTime(), ZoneId.of(request.getTimezone()));

                event.setTime(newStart, newEnd);
            } else if (request.getStartTime() != null) {
                event.setStartTime(ZonedDateTime.of(request.getStartTime(), ZoneId.of(request.getTimezone())));
            } else if (request.getEndTime() != null) {
                event.setEndTime(ZonedDateTime.of(request.getEndTime(), ZoneId.of(request.getTimezone())));
            } else {
                ZonedDateTime newStart = ZonedDateTime.of(event.getStartTime().toLocalDateTime(), ZoneId.of(request.getTimezone()));
                ZonedDateTime newEnd = ZonedDateTime.of(event.getEndTime().toLocalDateTime(), ZoneId.of(request.getTimezone()));

                event.setTime(newStart, newEnd);
            }
        }

        if (request.getHost() != null) {
            User newHost = this.userService.getUserByEmail(request.getHost());
            event.setHost(newHost);
        }

        this.eventRepository.save(event);

        return this.eventToResponse(event);
    }

    public void deleteEvent(String id) throws Exception {
        User user = this.userService.getCurrentUser();
        Event event = this.getEventById(id);

        if (!user.equals(event.getHost())) {
            throw new Exception("Cannot delete event if you're not the host.");
        }
        this.eventRepository.deleteById(UUID.fromString(id));
    }

    public EventResponse addParticipants(String id, AddParticipantsToEventRequest request) throws Exception {
        Event event = this.getEventById(id);
        List<String> emails = request.getEmails();

        emails.forEach(email -> {
            try {
                this.eventParticipantService.createEventParticipant(email, event);
            } catch (Exception e) {
                // do nothing
            }
        });

        return this.eventToResponse(event);
    }

    public EventResponse removeParticipant(String eventId, String inviteId) throws Exception {
        Event event = this.getEventById(eventId);
        this.eventParticipantService.deleteEventParticipantById(inviteId);

        return this.eventToResponse(event);
    }
}
