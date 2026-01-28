package event_scheduler_api.api.dto.request;

import event_scheduler_api.api.model.EventParticipant;
import event_scheduler_api.api.model.User;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class EventRequest {
    private String name;
    private User host;
    private Date startTime;
    private Date endTime;
    private List<EventParticipant> participants;
}
