package event_scheduler_api.api.repository;

import event_scheduler_api.api.model.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, String> {
}
