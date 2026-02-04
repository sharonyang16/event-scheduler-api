package event_scheduler_api.api.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
@Table(name = "users")
public class User extends  BaseEntity{
    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @OneToMany(mappedBy = "host")
    @Getter(AccessLevel.NONE)
    private List<Event> hostingEvents = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @Getter(AccessLevel.NONE)
    private List<EventParticipant> participatingEvents = new ArrayList<>();

    @ManyToMany
    @Getter(AccessLevel.NONE)
    private List<User> friends = new ArrayList<>();

    @CreatedDate
    @Column(name = "time_joined", nullable = false, updatable = false)
    private Instant timeJoined;

    public List<Event> getHostingEvents() {
        return new ArrayList<>(this.hostingEvents);
    }

    public List<EventParticipant> getParticipatingEvents() {
        return new ArrayList<>(this.participatingEvents);
    }

    public List<User> getFriends() {
        return new ArrayList<>(this.friends);
    }

    public void addFriend(User u) {
        if (this.friends.contains(u)) {
            this.friends.add(u);
            u.addFriend(this);
        }

    }

    public void removeFriend(User u) {
        if (this.friends.contains(u)) {
            this.friends.remove(u);
            u.removeFriend(this);
        }
    }

}
