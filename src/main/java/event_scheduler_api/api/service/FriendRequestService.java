package event_scheduler_api.api.service;

import event_scheduler_api.api.dto.request.CreateFriendRequestRequest;
import event_scheduler_api.api.model.FriendRequest;
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
        User user = this.userService.getCurrentUser();
        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setSender(user);

        this.friendRequestRepository.save(friendRequest);
    }
}
