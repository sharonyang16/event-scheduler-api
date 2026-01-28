package event_scheduler_api.api.model;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "event")
public class Event {
    @Id
    @Column(name = "event_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String eventId;
    private String name;
    // @OneToOne
    // @JoinColumn
    //private User host;
    private String host;
    private Date startDate;
    private Date endDate;
    @OneToMany
    @JoinColumn
    private List<EventParticipantStatus> participants;

    public Event(String name, String host, Date startDate, Date endDate) throws Exception {
        if (name.isEmpty()) {
            throw new Exception("Event name cannot be blank!");
        }
        if (startDate.after(endDate)) {
            throw new Exception("Event start time must be before event end!");
        }
        this.name = name;
        this.host = host;
        this.startDate = startDate;
        this.endDate = endDate;
        this.participants = new ArrayList<EventParticipantStatus>();
    }

    protected Event() {
    }

    public String getId() {
        return this.eventId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) throws Exception {
        if (name.isEmpty()) {
            throw new Exception("Event name cannot be blank!");
        }
        this.name = name;
    }

    public String getHost() {
        return this.host;
    }

    private boolean isUUIDValid(String uuid) {
        try {
            UUID.fromString(uuid);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public void setHost(String host) throws Exception {
        if (isUUIDValid((host))) {
            throw new Exception("Event name cannot be blank!");
        }
        this.host = host;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(Date startDate) throws Exception {
        if (startDate.after(this.endDate)) {
            throw new Exception("Event start date cannot be after event end!");
        }
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(Date endDate) throws Exception {
        if (endDate.before(this.startDate)) {
            throw new Exception("Event end date cannot be before event start!");
        }
        this.endDate = endDate;
    }

    /*
    public EventParticipantStatus[] getParticipants() {
        return this.participants.clone();
    }
    public void setParticipants(EventParticipantStatus[] participants) throws Exception {
        this.participants = participants;
    } */

    // public void addParticipant

    // public void removeParticipant
}


