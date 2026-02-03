package event_scheduler_api.api.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;


@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "event")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "eventId"
)
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
    private List<EventParticipant> participants = new ArrayList<>();

    @CreatedDate
    @Column(name = "time_created", nullable = false, updatable = false)
    private Instant timeCreated;

    @LastModifiedDate
    @Column(name = "time_updated", nullable = false)
    private Instant timeUpdated;

    public void setTime(ZonedDateTime startTime, ZonedDateTime endTime) throws Exception {
        if (startTime.isAfter(endTime)) {
            throw new Exception("Event start date cannot be after event end!");
        }
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public void setStartTime(ZonedDateTime startTime) throws Exception {
        if (this.endTime != null) {
            if (startTime.isAfter(this.endTime)) {
                throw new Exception("Event start date cannot be after event end!");
            }
        }
        this.startTime = startTime;
    }

    public void setEndTime(ZonedDateTime endTime) throws Exception {
        if (this.startTime != null) {
            if (endTime.isBefore(this.startTime)) {
                throw new Exception("Event end date cannot be before event start!");
            }
        }
        this.endTime = endTime;
    }

    public List<EventParticipant> getParticipants() {
        return new ArrayList<>(this.participants);
    }

    public void addParticipant(EventParticipant participant) {
        this.participants.add(participant);
        participant.setEvent(this);
    }

    public void removeParticipant(EventParticipant participant) {
        participants.remove(participant);
    }

}


