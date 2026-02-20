package event_scheduler_api.dto.request;

import event_scheduler_api.model.EventParticipationStatus;
import lombok.Data;

@Data
public class EventParticipantRequest {
    String email;
    EventParticipationStatus status;
}
