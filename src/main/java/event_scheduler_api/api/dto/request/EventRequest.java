package event_scheduler_api.api.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import event_scheduler_api.api.model.EventParticipant;
import event_scheduler_api.api.model.User;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class EventRequest {
    private String name;
    private String host;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    private String timezone;
    private List<EventParticipant> participants;
}
