package com.uokclubmanagement.service;

import com.uokclubmanagement.entity.Club;
import com.uokclubmanagement.entity.Event;
import com.uokclubmanagement.repository.ClubRepository;
import com.uokclubmanagement.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private ClubRepository clubRepository;
    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @Override
    public Event createEvent(Event event, String clubId) {

        // Find club is exist
        Optional<Club> clubOptional = Optional.ofNullable(clubRepository.findClubByClubId(clubId));

        if (clubOptional.isPresent()) {

            // Set the eventId
            if (event.getEventId() == null || event.getEventId().isEmpty()) {
                long seqValue = sequenceGeneratorService.generateSequence("Events Sequence");
                String eventId = String.format("Event-%04d", seqValue);
                event.setEventId(eventId);
            }

            // Check the date is valid
            LocalDate currentDate = LocalDate.now();

            /* Event should display to member before at least three days */
            LocalDate threeDaysBeforeEventDate = event.getEventDate().minusDays(3);
            if(!currentDate.isBefore(threeDaysBeforeEventDate)){
                throw new RuntimeException("The event date is not at least three days before the current date");
            }

            // Set publish date and time
            LocalTime currentTime = LocalTime.now();
            LocalTime timeWithoutSeconds = currentTime.withNano(0);

            event.setPublishedDate(currentDate);
            event.setPublishedTime(timeWithoutSeconds);
            // Set the organizing club
            event.setOrganizingClub(clubId);
        }
        return eventRepository.save(event);
    }

    @Override
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @Override
    public Event updateEventById(String eventId, Event event) {
        return null;
    }

    @Override
    public void deleteEventById(String eventId) {

    }

    @Override
    public List<Event> getAllEventsByOrganizingClub(String clubId) {
        return List.of();
    }

    @Override
    public List<Event> getAllOngoingEventsByOrganizingClub(String clubId) {
        return List.of();
    }

    @Override
    public List<Event> getAllUpcomingEventsByOrganizingClub(String clubId) {
        return List.of();
    }

}
