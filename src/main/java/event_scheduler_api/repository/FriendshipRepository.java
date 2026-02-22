package event_scheduler_api.repository;

import event_scheduler_api.model.Friendship;
import event_scheduler_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FriendshipRepository extends JpaRepository<Friendship, UUID> {
    Optional<Friendship> findFriendshipByUser1AndUser2(User user1, User user2);


    @Query("SELECT f FROM Friendship f WHERE f.user1 = :user OR f.user2 = :user")
    List<Friendship> findFriendshipsByUser(@Param("user") User user);

    List<Friendship> findFriendshipByUser1OrUser2(User user1, User user2);
}
