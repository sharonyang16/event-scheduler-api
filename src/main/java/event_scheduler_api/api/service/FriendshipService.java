package event_scheduler_api.api.service;

import event_scheduler_api.api.dto.response.FriendshipResponse;
import event_scheduler_api.api.mapper.FriendshipMapper;
import event_scheduler_api.api.model.Friendship;
import event_scheduler_api.api.model.User;
import event_scheduler_api.api.repository.FriendshipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FriendshipService {
    private final FriendshipRepository friendshipRepository;
    private final UserService userService;
    private final FriendshipMapper friendshipMapper;

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

    public List<FriendshipResponse> getMyFriends() throws Exception {
        User user = this.userService.getCurrentUser();

        List<Friendship> friendships = this.friendshipRepository.findFriendshipsByUser(user);

        return friendships.stream().map(friendship -> {
            User friend = friendship.getUser1().equals(user) ? friendship.getUser2() : friendship.getUser1();
            return this.friendshipMapper.toFriendshipResponse(friendship, friend);
        }).toList();
    }

    public boolean checkFriendship(String id) throws Exception {
        User user1 = this.userService.getCurrentUser();
        User user2 = this.userService.getUserById(id);

        return this.areFriends(user1, user2);
    }

    public void deleteFriendshipById(String id) throws Exception {
        User user = this.userService.getCurrentUser();
        Friendship friendship = this.getFriendshipById(id);

        if (!(user.equals(friendship.getUser1()) || user.equals(friendship.getUser2()))) {
            throw new Exception("You cannot remove this friendship!");
        }

        this.friendshipRepository.deleteById(UUID.fromString(id));
    }

}
