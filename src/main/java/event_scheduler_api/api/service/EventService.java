package event_scheduler_api.api.service;

import event_scheduler_api.api.model.Event;
import event_scheduler_api.api.dto.request.EventRequest;
import event_scheduler_api.api.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event getEvent(String id) throws Exception {
        return eventRepository.findById(id).orElseThrow(() -> new Exception("Event not found!"));
    }

    public Event addEvent(EventRequest request) throws Exception {
        Event event = new Event(request.getName(), "", request.getStartDate(), request.getEndDate());
        eventRepository.save(event);
        return event;
    }

    public Event partialUpdate(String id, EventRequest request) throws Exception {
        Event event = eventRepository.findById(id).orElseThrow(() -> new Exception("Event not found!"));
        if (request.getName() != null) {
            event.setName(request.getName());
        }
        if (request.getStartDate() != null) {
            event.setStartDate(request.getStartDate());
        }
        if (request.getEndDate() != null) {
            event.setEndDate(request.getEndDate());
        }
        if (request.getParticipants() != null) {
            event.setHost(request.getHost());
        }

        eventRepository.save(event);

        return event;
    }

}
