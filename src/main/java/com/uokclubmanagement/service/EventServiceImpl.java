package com.uokclubmanagement.service;

import com.uokclubmanagement.entity.Club;
import com.uokclubmanagement.entity.Event;
import com.uokclubmanagement.entity.MainAdmin;
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
import java.util.stream.Collectors;

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
        Optional<Club> clubOptional = clubRepository.findById(clubId);

        if (clubOptional.isPresent()) {

            // Set the eventId
            if (event.getEventId() == null || event.getEventId().isEmpty()) {
                long seqValue = sequenceGeneratorService.generateSequence("Events Sequence");
                String eventId = String.format("Event-%04d", seqValue);
                event.setEventId(eventId);
            }

            // Validate date and time
            validateDateAndTime(event);

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

        // Find event is existing
        Optional<Event> findEvent = eventRepository.findById(eventId);
        if (findEvent.isPresent()) {
            Event exisitingEvent = findEvent.get();
            updateEventFields(exisitingEvent, event);

            // Save updated fields on events collection
            return eventRepository.save(exisitingEvent);
        }
        else {
            throw new RuntimeException("Invalid Event ID");
        }
    }

    @Override
    public void deleteEventById(String eventId) {
        Optional<Event> deleteEvent = eventRepository.findById(eventId);
        if (deleteEvent.isPresent()) {
            eventRepository.delete(deleteEvent.get());
            System.out.println("Deleted event: " + eventId);
        }
        else{
            throw new RuntimeException("Invalid Event ID: "+eventId);
        }
    }

    @Override
    public List<Event> getAllEventsByClubId(String clubId) {

        Optional<Club> findClub = clubRepository.findById(clubId);
        if (findClub.isPresent()) {
            return eventRepository.getAllEventsByOrganizingClub(clubId);
        }
        else {
            throw new RuntimeException("Invalid Club ID: "+clubId);
        }
    }

    @Override
    public List<Event> getAllOngoingEventsByClubId(String clubId) {

        LocalDate currentDate = LocalDate.now();

        // List events by clubId
        List<Event> events = eventRepository.getAllEventsByOrganizingClub(clubId);

        List<Event> ongoingEvents =  events.stream()
                .filter(event -> event.getEventDate().isEqual(currentDate))
                .collect(Collectors.toList());

        // Check event availability
        if (ongoingEvents.isEmpty()) {
            throw new RuntimeException("No ongoing events found");
        }
        return ongoingEvents;
    }

    @Override
    public List<Event> getAllUpcomingEventsByClubId(String clubId) {

        LocalDate currentDate = LocalDate.now();

        // List events by clubId
        List<Event> events = eventRepository.getAllEventsByOrganizingClub(clubId);

        List<Event> upcomingEvents =  events.stream()
                .filter(event -> event.getEventDate().isAfter(currentDate))
                .collect(Collectors.toList());

        // Check event availability
        if (upcomingEvents.isEmpty()) {
            throw new RuntimeException("No upcoming events found");
        }
        return upcomingEvents;
    }

    @Override
    public List<Event> getAllPastEventsByClubId(String clubId) {

        LocalDate currentDate = LocalDate.now();

        // List events by clubId
        List<Event> events = eventRepository.getAllEventsByOrganizingClub(clubId);

        List<Event> pastEvents =  events.stream()
                .filter(event -> event.getEventDate().isBefore(currentDate))
                .collect(Collectors.toList());

        // Check event availability
        if (pastEvents.isEmpty()) {
            throw new RuntimeException("No past events found");
        }
        return pastEvents;
    }

    @Override
    public Event getEventById(String eventId) {
        return eventRepository.findById(eventId).get();
    }

    @Override
    public List<Event> getAllOngoingEvents() {

        LocalDate currentDate = LocalDate.now();

        // List all events
        List<Event> events = eventRepository.findAll();

        List<Event> allOngoingEvents =  events.stream()
                .filter(event -> event.getEventDate().isEqual(currentDate))
                .collect(Collectors.toList());

        // Check event availability
        if (allOngoingEvents.isEmpty()) {
            throw new RuntimeException("No ongoing events found");
        }
        return allOngoingEvents;
    }

    @Override
    public List<Event> getAllUpcomingEvents() {

        LocalDate currentDate = LocalDate.now();

        // List all events
        List<Event> events = eventRepository.findAll();

        List<Event> allUpComingEvents =  events.stream()
                .filter(event -> event.getEventDate().isAfter(currentDate))
                .collect(Collectors.toList());

        // Check event availability
        if (allUpComingEvents.isEmpty()) {
            throw new RuntimeException("No upcoming events found");
        }
        return allUpComingEvents;
    }

    @Override
    public List<Event> getAllPastEvents() {

        LocalDate currentDate = LocalDate.now();

        // List all events
        List<Event> events = eventRepository.findAll();

        List<Event> allPastEvents =  events.stream()
                .filter(event -> event.getEventDate().isBefore(currentDate))
                .collect(Collectors.toList());

        // Check event availability
        if (allPastEvents.isEmpty()) {
            throw new RuntimeException("No past events found");
        }
        return allPastEvents;
    }

    private void validateDateAndTime(Event event) {
        // Check the date is valid
        LocalDate currentDate = LocalDate.now();

        // Event should display to member before at least three days //
        LocalDate threeDaysBeforeEventDate = event.getEventDate().minusDays(3);
        if(!currentDate.isBefore(threeDaysBeforeEventDate)){
            throw new RuntimeException("The event date is not at least three days before the current date");
        }

        // Set publish date and time without seconds
        LocalTime currentTime = LocalTime.now();
        LocalTime timeWithoutSeconds = currentTime.withNano(0);

        event.setPublishedDate(currentDate);
        event.setPublishedTime(timeWithoutSeconds);
    }

    private void updateEventFields(Event exisitingEvent, Event event){

        if(exisitingEvent.getEventName() != null){
            exisitingEvent.setEventName(event.getEventName());
        }
        if(exisitingEvent.getEventType() != null){
            exisitingEvent.setEventType(event.getEventType());
        }
        if(exisitingEvent.getEventLocation() != null){
            exisitingEvent.setEventLocation(event.getEventLocation());
        }
        if(exisitingEvent.getEventDescription() != null){
            exisitingEvent.setEventDescription(event.getEventDescription());
        }
        if(exisitingEvent.getEventDate() != null){
            validateDateAndTime(event);
            exisitingEvent.setEventDate(event.getEventDate());
        }
        if(exisitingEvent.getEventTime() != null){
            exisitingEvent.setEventTime(event.getEventTime());
        }
    }
}
