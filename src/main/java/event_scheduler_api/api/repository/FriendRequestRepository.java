package event_scheduler_api.api.repository;

import event_scheduler_api.api.model.FriendRequest;
import event_scheduler_api.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, UUID> {
    Optional<FriendRequest> findFriendRequestBySenderAndReceiver(User sender, User receiver);

    Optional<List<FriendRequest>> findFriendRequestsBySender(User sender);

    Optional<List<FriendRequest>> findFriendRequestsByReceiver(User receiver);
}
