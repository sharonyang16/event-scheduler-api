package event_scheduler_api.api.repository;

import event_scheduler_api.api.model.FriendRequest;
import event_scheduler_api.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, String> {
    Optional<FriendRequest> findFriendRequestBySenderAndReceiver(User sender, User receiver);
}
