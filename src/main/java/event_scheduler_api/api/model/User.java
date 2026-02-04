package event_scheduler_api.api.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
@Table(name = "users")
public class User extends BaseEntity {
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
        if (!this.friends.contains(u)) {
            this.friends.add(u);
        }

    }

    public void removeFriend(User u) {
        this.friends.remove(u);
    }

}
