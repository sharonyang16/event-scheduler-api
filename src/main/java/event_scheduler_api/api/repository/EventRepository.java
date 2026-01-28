package event_scheduler_api.api.repository;

import event_scheduler_api.api.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, String> {
}
