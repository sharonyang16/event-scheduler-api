package event_scheduler_api.mapper;

import event_scheduler_api.dto.response.FriendshipResponse;
import event_scheduler_api.dto.response.ReceivedFriendRequestResponse;
import event_scheduler_api.dto.response.SentFriendRequestResponse;
import event_scheduler_api.model.FriendRequest;
import event_scheduler_api.model.Friendship;
import event_scheduler_api.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FriendshipMapper {
    private final UserMapper userMapper;

    public ReceivedFriendRequestResponse toReceivedFriendRequestResponse(FriendRequest friendRequest) {
        return ReceivedFriendRequestResponse.builder()
                .requestId(friendRequest.getId().toString())
                .sender(this.userMapper.toUserSummaryResponse(friendRequest.getSender()))
                .build();
    }

    public SentFriendRequestResponse toSentFriendRequestResponse(FriendRequest friendRequest) {
        return SentFriendRequestResponse.builder()
                .requestId(friendRequest.getId().toString())
                .receiver(this.userMapper.toUserSummaryResponse(friendRequest.getReceiver()))
                .build();
    }

    public FriendshipResponse toFriendshipResponse(Friendship friendship, User friend) {
        return FriendshipResponse.builder()
                .id(friendship.getId().toString())
                .friend(this.userMapper.toUserSummaryResponse(friend))
                .build();
    }
}
