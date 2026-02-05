package event_scheduler_api.api.service;

import event_scheduler_api.api.dto.response.EventInviteResponse;
import event_scheduler_api.api.model.EventParticipant;
import event_scheduler_api.api.model.EventParticipationStatus;
import event_scheduler_api.api.model.User;
import event_scheduler_api.api.repository.EventParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EventParticipantService {
    @Autowired
    EventParticipantRepository eventParticipantRepository;

    @Autowired
    UserService userService;

    @Autowired
    EventService eventService;

    private EventInviteResponse eventParticipantToInviteResponse(EventParticipant eventParticipant) {
        return EventInviteResponse.builder()
                .inviteId(eventParticipant.getId().toString())
                .event(this.eventService.eventToResponse(eventParticipant.getEvent()))
                .status(eventParticipant.getStatus())
                .build();
    }

    public EventParticipant getEventParticipantById(String id) throws Exception {
        return this.eventParticipantRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new Exception("Event participant with id " + id + " not found."));
    }

    public List<EventInviteResponse> getMyInvites() throws Exception {
        User user = this.userService.getCurrentUser();
        List<EventParticipant> eventParticipants = this.eventParticipantRepository.getEventParticipantsByUser(user);

        return eventParticipants.stream().map(this::eventParticipantToInviteResponse).toList();
    }

    public void updateEventParticipationStatusById(String id, EventParticipationStatus status) throws Exception {
        User user = this.userService.getCurrentUser();
        EventParticipant eventParticipant = this.getEventParticipantById(id);

        if (!user.equals(eventParticipant.getUser())) {
            throw new Exception("You cannot change this user's event attendance status!");
        }

        eventParticipant.setStatus(status);
        this.eventParticipantRepository.save(eventParticipant);
    }
}
