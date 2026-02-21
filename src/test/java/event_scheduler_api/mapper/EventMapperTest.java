package event_scheduler_api.mapper;

import event_scheduler_api.model.Event;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EventMapperTest {
    private final EventMapper eventMapper = new EventMapper();

    @Test
    @DisplayName("toEventResponse should return EventResponse obj when given Event is valid")
    void toEventResponseValid() {
    }

}