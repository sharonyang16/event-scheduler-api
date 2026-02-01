package event_scheduler_api.api.service;

import event_scheduler_api.api.model.Event;
import event_scheduler_api.api.dto.request.EventRequest;
import event_scheduler_api.api.model.User;
import event_scheduler_api.api.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    public List<Event> getAllEvents() {
        return this.eventRepository.findAll();
    }

    public Event getEvent(String id) throws Exception {
        return this.eventRepository.findById(id).orElseThrow(() -> new Exception("Event not found!"));
    }

    public Event addEvent(EventRequest request) throws Exception {
        Event event = new Event(
                request.getName(),
                new User(),
                ZonedDateTime.of(request.getStartTime(),ZoneId.of(request.getTimezone())),
                ZonedDateTime.of(request.getEndTime(), ZoneId.of(request.getTimezone())));
        this.eventRepository.save(event);
        return event;
    }

    public Event partialUpdate(String id, EventRequest request) throws Exception {
        Event event = this.eventRepository.findById(id).orElseThrow(() -> new Exception("Event not found!"));
        if (request.getName() != null) {
            event.setName(request.getName());
        }
        if (request.getStartTime() != null) {
            if (request.getTimezone() != null) {
                event.setStartTime(ZonedDateTime.of(request.getStartTime(), ZoneId.of(request.getTimezone())));
            } else {
                throw new Exception("Timezone must be specified!");
            }
        }
        if (request.getEndTime() != null) {
            if (request.getTimezone() != null) {
                event.setEndTime(ZonedDateTime.of(request.getEndTime(), ZoneId.of(request.getTimezone())));
            } else {
                throw new Exception("Timezone must be specified!");
            }
        }
        if (request.getParticipants() != null) {
            event.setHost(request.getHost());
        }

        this.eventRepository.save(event);

        return event;
    }

}
