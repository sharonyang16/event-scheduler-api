package event_scheduler_api.api.service;

import event_scheduler_api.api.dto.response.EventParticipantResponse;
import event_scheduler_api.api.dto.response.EventResponse;
import event_scheduler_api.api.dto.response.UserContactResponse;
import event_scheduler_api.api.model.Event;
import event_scheduler_api.api.dto.request.EventRequest;
import event_scheduler_api.api.model.User;
import event_scheduler_api.api.repository.EventRepository;
import event_scheduler_api.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    private EventResponse eventToResponse(Event event, User user) {
        return EventResponse.builder()
                .eventId(event.getEventId())
                .name(event.getName())
                .host(UserContactResponse.builder()
                        .email(user.getEmail())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .build())
                .startTime(event.getStartTime().toLocalDateTime())
                .endTime(event.getEndTime().toLocalDateTime())
                .timezone(event.getStartTime().getZone().getDisplayName(TextStyle.SHORT, Locale.US))
                .participants(
                        event.getParticipants().stream().map(
                                        eventParticipant -> EventParticipantResponse.builder()
                                                .email(eventParticipant.getUser().getEmail())
                                                .firstName(eventParticipant.getUser().getFirstName())
                                                .lastName(eventParticipant.getUser().getLastName())
                                                .confirmed(eventParticipant.getConfirmed())
                                                .timeCreated(eventParticipant.getTimeCreated())
                                                .timeUpdated(eventParticipant.getTimeUpdated())
                                                .build())
                                .collect(Collectors.toList())
                )

                .build();
    }

    public List<EventResponse> getAllEvents() throws Exception {
        List<Event> events = this.eventRepository.findAll();

        return events.stream().map(event -> {
            User user = this.userRepository.findByUserId(event.getHost().getUserId()).orElseThrow();
            return this.eventToResponse(event, user);
        }).collect(Collectors.toList());
    }

    public Event getEvent(String id) throws Exception {
        return this.eventRepository.findById(id).orElseThrow(() -> new Exception("Event not found!"));
    }

    public EventResponse addEvent(EventRequest request) throws Exception {
        User user = this.userRepository.findByUserId(request.getHost()).orElseThrow(
                () -> new Exception("User does not exist!"));

        Event event = new Event();
        event.setName(request.getName());
        event.setHost(user);
        event.setStartTime(ZonedDateTime.of(request.getStartTime(), ZoneId.of(request.getTimezone())));
        event.setEndTime(ZonedDateTime.of(request.getEndTime(), ZoneId.of(request.getTimezone())));


        this.eventRepository.save(event);

        return this.eventToResponse(event, user);
    }

    public Event partialUpdate(String id, EventRequest request) throws Exception {
        Event event = this.eventRepository.findById(id).orElseThrow(() -> new Exception("Event not found!"));
        if (request.getName() != null) {
            event.setName(request.getName());
        }
        if (request.getStartTime() != null) {
            if (request.getTimezone() != null) {
                event.setStartTime(ZonedDateTime.of(request.getStartTime(), ZoneId.of(request.getTimezone())));
            } else {
                throw new Exception("Timezone must be specified!");
            }
        }
        if (request.getEndTime() != null) {
            if (request.getTimezone() != null) {
                event.setEndTime(ZonedDateTime.of(request.getEndTime(), ZoneId.of(request.getTimezone())));
            } else {
                throw new Exception("Timezone must be specified!");
            }
        }
        if (request.getParticipants() != null) {
            User user = this.userRepository.findByUserId(request.getHost()).orElseThrow(
                    () -> new Exception("User does not exist!"));
            event.setHost(user);
        }

        this.eventRepository.save(event);

        return event;
    }

    public void deleteEvent(String id) {
        this.eventRepository.deleteById(id);
    }
}
