package event_scheduler_api.api.repository;

import event_scheduler_api.api.model.EventParticipant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventParticipantRepository extends JpaRepository<EventParticipant, String> {
}
