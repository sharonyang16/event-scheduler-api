package event_scheduler_api.api.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "user")
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String userId;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @OneToMany(mappedBy = "host")
    @Getter(AccessLevel.NONE)
    private List<Event> hostingEvents;

    @OneToMany(mappedBy = "user")
    @Getter(AccessLevel.NONE)
    private List<EventParticipant> participatingEvents;

    public List<Event> getHostingEvents() {
        return new ArrayList<Event>(this.hostingEvents);
    }

    public List<EventParticipant> getParticipatingEvents() {
        return new ArrayList<EventParticipant>(this.participatingEvents);
    }
}
