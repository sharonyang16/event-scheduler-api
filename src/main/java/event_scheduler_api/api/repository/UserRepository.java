package event_scheduler_api.api.repository;

import event_scheduler_api.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
