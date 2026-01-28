package event_scheduler_api.api.dto.request;

import event_scheduler_api.api.model.EventParticipantStatus;
import lombok.Data;

import java.util.Date;

@Data
public class EventRequest {
    private String name;
    private String host;
    private Date startDate;
    private Date endDate;
    private EventParticipantStatus[] participants;
}
