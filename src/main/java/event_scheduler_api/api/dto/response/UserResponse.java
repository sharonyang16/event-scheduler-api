package event_scheduler_api.api.dto.response;

import event_scheduler_api.api.model.Event;
import event_scheduler_api.api.model.EventParticipant;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Value
@Builder
public class UserResponse {
    String userId;
    String email;
    String firstName;
    String lastName;
    List<Event> hostingEvents;
    List<EventParticipant> participatingEvents;
    Instant createdAt;
    Instant updatedAt;
}
