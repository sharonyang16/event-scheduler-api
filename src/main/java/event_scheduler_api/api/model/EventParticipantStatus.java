package event_scheduler_api.api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "event_participant_status")
public class EventParticipantStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    @ManyToOne
    @JoinColumn
    private Event event;
    private String user;
    private boolean confirmed;

    public EventParticipantStatus(Event event, String userId) {
        this.event = event;
        this.user = userId;
        this.confirmed = false;
    }

    protected EventParticipantStatus() {
    }

    public String getId() {
        return this.id;
    }

    public Event getEvent() {
        return this.event;
    }

    public String getUser() {
        return this.user;
    }

    public boolean getConfirmed() {
        return this.confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }
}
