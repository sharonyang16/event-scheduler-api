package event_scheduler_api.api.dto.request;

import event_scheduler_api.api.model.EventParticipantStatus;

import java.util.Date;

public class EventRequest {
    private String name;

    private String host;
    private Date startDate;
    private Date endDate;
    private EventParticipantStatus[] participants;


    public String getName() {
        return this.name;
    }

    public String getHost() {
        return this.host;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public EventParticipantStatus[] getParticipants() {
        return this.participants;
    }

}
