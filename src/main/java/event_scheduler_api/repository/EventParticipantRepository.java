package event_scheduler_api.repository;

import event_scheduler_api.model.EventParticipant;
import event_scheduler_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EventParticipantRepository extends JpaRepository<EventParticipant, UUID> {
    List<EventParticipant> getEventParticipantsByUser(User user);
}
