package event_scheduler_api.api.model;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.ArrayList;
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

    @ManyToOne
    @JoinColumn(name = "host_id", nullable = false)
    private User host;

    @Column(name = "start_time", nullable = false)
    @Setter(AccessLevel.NONE)
    private ZonedDateTime startTime;

    @Column(name = "end_time", nullable = false)
    @Setter(AccessLevel.NONE)
    private ZonedDateTime endTime;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    @Getter(AccessLevel.NONE)
    private List<EventParticipant> participants;

    @CreatedDate
    @Column(name = "time_created", nullable = false)
    private Instant timeCreated;

    @LastModifiedDate
    @Column(name = "time_updated", nullable = false)
    private Instant timeUpdated;

    public Event(String name, User host, ZonedDateTime startTime, ZonedDateTime endTime) throws Exception {
        if (name.isEmpty()) {
            throw new Exception("Event name cannot be blank!");
        }
        if (startTime.isAfter(endTime)) {
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


    public void setStartTime(ZonedDateTime startTime) throws Exception {
        if (startTime.isAfter(this.endTime)) {
            throw new Exception("Event start date cannot be after event end!");
        }
        this.startTime = startTime;
    }

    public void setEndTime(ZonedDateTime endTime) throws Exception {
        if (endTime.isBefore(this.startTime)) {
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


