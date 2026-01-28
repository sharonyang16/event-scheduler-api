package event_scheduler_api.api.model;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Data
@Entity
@Table(name = "event")
public class Event {
    @Id
    @Column(name = "event_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String eventId;

    @Column(nullable = false)
    private String name;

    @OneToOne
    @JoinColumn(name = "host_id", nullable = false)
    private User host;

    @Column(name = "start_time", nullable = false)
    @Setter(AccessLevel.NONE)
    private Date startTime;

    @Column(name = "end_time", nullable = false)
    @Setter(AccessLevel.NONE)
    private Date endTime;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    @Getter(AccessLevel.NONE)
    private List<EventParticipant> participants;

    public Event(String name, User host, Date startTime, Date endTime) throws Exception {
        if (name.isEmpty()) {
            throw new Exception("Event name cannot be blank!");
        }
        if (startTime.after(endTime)) {
            throw new Exception("Event start time must be before event end!");
        }
        this.name = name;
        this.host = host;
        this.startTime = startTime;
        this.endTime = endTime;
        this.participants = new ArrayList<EventParticipant>();
    }

    protected Event() {
    }


    public void setStartTime(Date startTime) throws Exception {
        if (startTime.after(this.endTime)) {
            throw new Exception("Event start date cannot be after event end!");
        }
        this.startTime = startTime;
    }

    public void setEndTime(Date endTime) throws Exception {
        if (endTime.before(this.startTime)) {
            throw new Exception("Event end date cannot be before event start!");
        }
        this.endTime = endTime;
    }

    public List<EventParticipant> getParticipants() {
        return new ArrayList<EventParticipant>(this.participants);
    }

    // TODO: public void addParticipant

    // TODO: public void removeParticipant
}


