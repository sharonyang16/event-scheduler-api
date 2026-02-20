package event_scheduler_api.dto.response;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class FriendshipResponse {
    String id;
    UserSummaryResponse friend;
}
