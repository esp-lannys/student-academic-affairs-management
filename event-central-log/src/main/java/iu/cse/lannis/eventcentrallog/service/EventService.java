package iu.cse.lannis.eventcentrallog.service;

import iu.cse.lannis.eventcentrallog.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public <T> void createEvent(String topic, String payload) {

    }
}
