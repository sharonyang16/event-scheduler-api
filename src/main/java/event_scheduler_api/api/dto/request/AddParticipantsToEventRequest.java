package event_scheduler_api.api.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class AddParticipantsToEventRequest {
    private List<String> emails;
}
