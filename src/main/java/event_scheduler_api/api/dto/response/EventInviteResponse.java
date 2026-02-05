package event_scheduler_api.api.dto.response;

import event_scheduler_api.api.model.EventParticipationStatus;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class EventInviteResponse {
    String inviteId;
    EventResponse event;
    EventParticipationStatus status;
}
