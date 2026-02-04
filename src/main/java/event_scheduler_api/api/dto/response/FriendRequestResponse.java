package event_scheduler_api.api.dto.response;

import event_scheduler_api.api.model.FriendRequestStatus;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class FriendRequestResponse {
    UserSummaryResponse sender;
    UserSummaryResponse receiver;
    FriendRequestStatus status;
}
