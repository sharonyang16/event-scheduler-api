package event_scheduler_api.api.service;

import event_scheduler_api.api.model.Friendship;
import event_scheduler_api.api.model.User;
import event_scheduler_api.api.repository.FriendshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FriendshipService {
    @Autowired
    private FriendshipRepository friendshipRepository;

    public Friendship getFriendshipById(String id) throws Exception {
        return this.friendshipRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new Exception("Friendship with id " + id + " not found."));
    }

    public boolean areFriends(User user1, User user2) {
        return this.friendshipRepository.findFriendshipByUser1AndUser2(user1, user2).isPresent() ||
                this.friendshipRepository.findFriendshipByUser1AndUser2(user2, user1).isPresent();
    }

    public void createFriendship(User user1, User user2) throws Exception {
        if (user1.equals(user2)) {
            throw new Exception("User cannot create a friendship with themselves.");
        }

        if (this.areFriends(user1, user2)) {
            throw new Exception("Friendship already exist!");
        }

        Friendship friendship = new Friendship();
        friendship.setUser1(user1);
        friendship.setUser2(user2);

        this.friendshipRepository.save(friendship);
    }

}
