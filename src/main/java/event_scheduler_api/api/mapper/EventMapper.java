package event_scheduler_api.api.mapper;

import event_scheduler_api.api.dto.response.EventInviteResponse;
import event_scheduler_api.api.dto.response.EventParticipantResponse;
import event_scheduler_api.api.dto.response.EventResponse;
import event_scheduler_api.api.dto.response.UserContactResponse;
import event_scheduler_api.api.model.Event;
import event_scheduler_api.api.model.EventParticipant;
import org.springframework.stereotype.Component;

import java.time.format.TextStyle;
import java.util.Locale;
import java.util.stream.Collectors;

@Component
public class EventMapper {
    public EventResponse toEventResponse(Event event) {
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
                                        this::toEventParticipantResponse)
                                .collect(Collectors.toList())
                )
                .createdAt(event.getCreatedAt())
                .updatedAt(event.getUpdatedAt())
                .build();
    }

    public EventParticipantResponse toEventParticipantResponse(EventParticipant eventParticipant) {
        return EventParticipantResponse.builder()
                .email(eventParticipant.getUser().getEmail())
                .firstName(eventParticipant.getUser().getFirstName())
                .lastName(eventParticipant.getUser().getLastName())
                .status(eventParticipant.getStatus())
                .createdAt(eventParticipant.getCreatedAt())
                .updatedAt(eventParticipant.getUpdatedAt())
                .build();
    }
    public EventInviteResponse toEventInviteResponse(EventParticipant eventParticipant) {
        return EventInviteResponse.builder()
                .inviteId(eventParticipant.getId().toString())
                .event(toEventResponse(eventParticipant.getEvent()))
                .status(eventParticipant.getStatus())
                .build();
    }
}
