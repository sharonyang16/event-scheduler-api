package event_scheduler_api.dto.response;

import event_scheduler_api.model.EventParticipationStatus;
import lombok.Builder;
import lombok.Value;

import java.time.Instant;

@Value
@Builder
public class EventParticipantResponse {
    String email;
    String firstName;
    String lastName;
    EventParticipationStatus status;
    Instant createdAt;
    Instant updatedAt;
}
