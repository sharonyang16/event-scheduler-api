package event_scheduler_api.api.dto.response;

import lombok.Builder;
import lombok.Value;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Value
@Builder
public class EventResponse {
    String eventId;
    String name;
    UserContactResponse host;
    LocalDateTime startTime;
    LocalDateTime endTime;
    String timezone;
    List<EventParticipantResponse> participants;
    Instant createdAt;
    Instant updatedAt;
}
