package event_scheduler_api.api.service;

import event_scheduler_api.api.dto.request.CreateFriendRequestRequest;
import event_scheduler_api.api.model.FriendRequest;
import event_scheduler_api.api.repository.FriendRequestRepository;
import event_scheduler_api.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FriendRequestService {
    @Autowired
    private FriendRequestRepository friendRequestRepository;

    @Autowired
    private UserRepository userRepository;

    public FriendRequest createFriendRequest(CreateFriendRequestRequest request) {

        FriendRequest friendRequest = new FriendRequest();

        this.friendRequestRepository.save(friendRequest);
    }
}
