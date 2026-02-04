package event_scheduler_api.api.dto.response;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserSummaryResponse {
    String id;
    String email;
    String firstName;
    String lastName;
}
