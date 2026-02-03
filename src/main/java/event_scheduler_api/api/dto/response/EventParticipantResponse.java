package event_scheduler_api.api.dto.response;

import event_scheduler_api.api.model.EventParticipationStatus;
import lombok.Builder;
import lombok.Value;

import java.time.Instant;

@Value
@Builder
public class EventParticipantResponse {
    String email;
    String firstName;
    String lastName;
    EventParticipationStatus confirmed;
    Instant timeCreated;
    Instant timeUpdated;
}
