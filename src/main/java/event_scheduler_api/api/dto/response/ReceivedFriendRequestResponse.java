package event_scheduler_api.api.dto.response;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ReceivedFriendRequestResponse {
    String requestId;
    UserSummaryResponse sender;
}
