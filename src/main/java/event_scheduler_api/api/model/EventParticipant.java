package event_scheduler_api.api.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;

@Data
@Entity
@Table(name = "event_participant")
public class EventParticipant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private boolean confirmed;

    @CreatedDate
    @Column(name = "time_created", nullable = false, updatable = false)
    private Instant timeCreated;

    @LastModifiedDate
    @Column(name = "time_updated",nullable = false)
    private Instant timeUpdated;

}
