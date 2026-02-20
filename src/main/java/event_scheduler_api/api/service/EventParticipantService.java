package event_scheduler_api.api.service;

import event_scheduler_api.api.dto.response.EventInviteResponse;
import event_scheduler_api.api.mapper.EventMapper;
import event_scheduler_api.api.model.Event;
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
    private EventParticipantRepository eventParticipantRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private EventMapper eventMapper;

    public void createEventParticipant(String email, Event event) throws Exception {
        EventParticipant eventParticipant = new EventParticipant();
        eventParticipant.setEvent(event);
        eventParticipant.setUser(this.userService.getUserByEmail(email));
        eventParticipant.setStatus(EventParticipationStatus.PENDING);

        this.eventParticipantRepository.save(eventParticipant);
    }

    public void deleteEventParticipantById(String id) {
        this.eventParticipantRepository.deleteById(UUID.fromString(id));
    }

    public EventParticipant getEventParticipantById(String id) throws Exception {
        return this.eventParticipantRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new Exception("Event participant with id " + id + " not found."));
    }

    public List<EventInviteResponse> getMyInvites(EventParticipationStatus status) throws Exception {
        User user = this.userService.getCurrentUser();
        List<EventParticipant> eventParticipants = this.eventParticipantRepository.getEventParticipantsByUser(user);

        return eventParticipants.stream()
                .filter(eventParticipant -> status == null || eventParticipant.getStatus().equals(status))
                .map(eventParticipant -> this.eventMapper.toEventInviteResponse(eventParticipant)).toList();
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
