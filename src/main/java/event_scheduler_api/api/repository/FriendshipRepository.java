package event_scheduler_api.api.repository;

import event_scheduler_api.api.model.Friendship;
import event_scheduler_api.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface FriendshipRepository  extends JpaRepository<Friendship, UUID> {
    Optional<Friendship> findFriendshipByUser1AndUser2(User user1, User user2);
}
