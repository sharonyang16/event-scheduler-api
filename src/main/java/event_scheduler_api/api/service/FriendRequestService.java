package event_scheduler_api.api.service;

import event_scheduler_api.api.dto.request.CreateFriendRequestRequest;
import event_scheduler_api.api.model.FriendRequest;
import event_scheduler_api.api.model.FriendRequestStatus;
import event_scheduler_api.api.model.User;
import event_scheduler_api.api.repository.FriendRequestRepository;
import event_scheduler_api.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FriendRequestService {
    @Autowired
    private FriendRequestRepository friendRequestRepository;

    @Autowired UserService userService;

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

        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setSender(sender);
        friendRequest.setReceiver(receiver);
        friendRequest.setStatus(FriendRequestStatus.PENDING);

        this.friendRequestRepository.save(friendRequest);

        return friendRequest;
    }

}
