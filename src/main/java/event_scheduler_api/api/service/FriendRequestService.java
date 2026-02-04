package event_scheduler_api.api.service;

import event_scheduler_api.api.dto.request.CreateFriendRequestRequest;
import event_scheduler_api.api.dto.response.FriendRequestResponse;
import event_scheduler_api.api.dto.response.UserResponse;
import event_scheduler_api.api.dto.response.UserSummaryResponse;
import event_scheduler_api.api.model.Event;
import event_scheduler_api.api.model.FriendRequest;
import event_scheduler_api.api.model.FriendRequestStatus;
import event_scheduler_api.api.model.User;
import event_scheduler_api.api.repository.FriendRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FriendRequestService {
    @Autowired
    private FriendRequestRepository friendRequestRepository;

    @Autowired
    UserService userService;

    @Autowired
    FriendshipService friendshipService;

    private FriendRequest getFriendRequestById(String id) throws Exception {
        return this.friendRequestRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new Exception("Friend request with id " + id + " not found."));
    }

    public FriendRequest createFriendRequest(CreateFriendRequestRequest request) throws Exception {
        User sender = this.userService.getCurrentUser();
        User receiver = this.userService.getUserByEmail(request.getReceiver());

        if (receiver.equals(sender)) {
            throw new Exception("You cannot send a friend request to yourself!");
        }

        if (this.friendRequestRepository.findFriendRequestBySenderAndReceiver(sender, receiver).isPresent()) {
            throw new Exception("You've already sent a friend request to this user!");
        }

        if (this.friendRequestRepository.findFriendRequestBySenderAndReceiver(receiver, sender).isPresent()) {
            throw new Exception("This user has sent a friend request to you!");
        }

        if (this.friendshipService.areFriends(sender, receiver)) {
            throw new Exception("You're already friends with this user!");
        }

        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setSender(sender);
        friendRequest.setReceiver(receiver);
        friendRequest.setStatus(FriendRequestStatus.PENDING);

        this.friendRequestRepository.save(friendRequest);

        return friendRequest;
    }

    public List<UserSummaryResponse> getMyReceived() throws Exception {
        User user = this.userService.getCurrentUser();
        List<FriendRequest> pendingRequests = this.friendRequestRepository.findFriendRequestsByReceiver(user)
                .orElse(new ArrayList<>());

        return pendingRequests.stream()
                .filter(friendRequest -> friendRequest.getStatus().equals(FriendRequestStatus.PENDING))
                .map(friendRequest -> this.userService.userToUserSummaryResponse(friendRequest.getSender()))
                .toList();
    }

    public List<UserSummaryResponse> getMySent() throws Exception {
        User user = this.userService.getCurrentUser();
        List<FriendRequest> pendingRequests = this.friendRequestRepository.findFriendRequestsBySender(user)
                .orElse(new ArrayList<>());

        return pendingRequests.stream()
                .filter(friendRequest -> friendRequest.getStatus().equals(FriendRequestStatus.PENDING))
                .map(friendRequest -> this.userService.userToUserSummaryResponse(friendRequest.getReceiver()))
                .toList();
    }

    public void acceptFriendRequestById(String id) throws Exception {
        FriendRequest friendRequest = this.getFriendRequestById(id);
        User user = this.userService.getCurrentUser();

        if (!user.equals(friendRequest.getReceiver())) {
            throw new Exception("You cannot accept this friend request!");
        }

        friendRequest.setStatus(FriendRequestStatus.ACCEPTED);
        this.friendRequestRepository.save(friendRequest);

        this.friendshipService.createFriendship(user, friendRequest.getSender());
        this.friendRequestRepository.delete(friendRequest);
    }

    public void rejectFriendRequestById(String id) throws Exception {
        FriendRequest friendRequest = this.getFriendRequestById(id);
        User user = this.userService.getCurrentUser();

        if (!user.equals(friendRequest.getReceiver())) {
            throw new Exception("You cannot reject this friend request!");
        }

        friendRequest.setStatus(FriendRequestStatus.REJECTED);
        this.friendRequestRepository.save(friendRequest);
        this.friendRequestRepository.delete(friendRequest);
    }

    public void deleteFriendRequestById(String id) throws Exception {
        FriendRequest friendRequest = this.getFriendRequestById(id);
        User user = this.userService.getCurrentUser();

        if (!user.equals(friendRequest.getSender())) {
            throw new Exception("You cannot delete this friend request!");
        }

        this.friendRequestRepository.delete(friendRequest);
    }
}
