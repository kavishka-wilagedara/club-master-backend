package com.uokclubmanagement.service;

import com.uokclubmanagement.entity.*;
import com.uokclubmanagement.repository.ClubAdminRepository;
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
    private ClubAdminRepository clubAdminRepository;
    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @Override
    public Event createEvent(Event event, String clubId,  String clubAdminId){

        // Find club and clubAdmin are exist
        Optional<ClubAdmin> clubAdminOptional = clubAdminRepository.findById(clubAdminId);
        Optional<Club> clubOptional = clubRepository.findById(clubId);

        if(clubAdminOptional.isEmpty()){
            throw new RuntimeException("Invalid Club Admin");
        }

        else if(clubOptional.isEmpty()){
            throw new RuntimeException("Invalid Club ID");
        }

        // Get clubAdmin

        ClubAdmin clubAdmin = clubAdminOptional.get();
        if (!clubAdmin.getClubId().equals(clubId)){
            throw new RuntimeException("Club ID does not match with Club Admin ID");
        }

        else {

            // Set the eventId
            if (event.getEventId() == null || event.getEventId().isEmpty()) {
                long seqValue = sequenceGeneratorService.generateSequence("Events Sequence");
                String eventId = String.format("Event-%04d", seqValue);
                event.setEventId(eventId);
            }

            // Validate date and time
            validateDateAndTime(event);

            // Set the organizing club and publisher name
            event.setResponseClub(clubId);
            event.setPublisherName(clubAdmin.getFullName());
            return eventRepository.save(event);
        }

    }

    @Override
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @Override
    public Event updateEventById(String clubAdminId, String eventId, Event event) {

        // Find event and clubAdmin are existing
        Optional<Event> findEvent = eventRepository.findById(eventId);
        Optional<ClubAdmin> findClubAdmin = clubAdminRepository.findById(clubAdminId);

        if(findEvent.isEmpty()) {
            throw new RuntimeException("Invalid Event ID");
        }

        else if(findClubAdmin.isEmpty()) {
            throw new RuntimeException("Invalid Club Admin ID");
        }

        else if (!findClubAdmin.get().getClubId().equals(findEvent.get().getResponseClub())){
            throw new RuntimeException("Club Admin ID does not match with response club ID");
        }

        else {
            Event exisitingEvent = findEvent.get();
            updateEventFields(exisitingEvent, event);
            contentScheduleUpdating(exisitingEvent, event);

            // Set publisher name
            exisitingEvent.setPublisherName(findClubAdmin.get().getFullName());

            // Save updated fields on events collection
            return eventRepository.save(exisitingEvent);
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
            return eventRepository.getAllEventsByResponseClub(clubId);
        }
        else {
            throw new RuntimeException("Invalid Club ID: "+clubId);
        }
    }

    @Override
    public List<Event> getAllOngoingEventsByClubId(String clubId) {

        LocalDate currentDate = LocalDate.now();

        // List events by clubId
        List<Event> events = eventRepository.getAllEventsByResponseClub(clubId);

        List<Event> ongoingEvents =  events.stream()
                .filter(event -> event.getScheduledDate().isEqual(currentDate))
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
        List<Event> events = eventRepository.getAllEventsByResponseClub(clubId);

        List<Event> upcomingEvents =  events.stream()
                .filter(event -> event.getScheduledDate().isAfter(currentDate))
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
        List<Event> events = eventRepository.getAllEventsByResponseClub(clubId);

        List<Event> pastEvents =  events.stream()
                .filter(event -> event.getScheduledDate().isBefore(currentDate))
                .collect(Collectors.toList());

        // Check event availability
        if (pastEvents.isEmpty()) {
            throw new RuntimeException("No past events found");
        }
        return pastEvents;
    }

    @Override
    public Event getEventById(String eventId) {

        Optional<Event> optionalEvent = eventRepository.findById(eventId);

        if (optionalEvent.isEmpty()){
            throw new RuntimeException("Event not found with: "+eventId);
        }

        return optionalEvent.get();
    }

    @Override
    public List<Event> getAllOngoingEvents() {

        LocalDate currentDate = LocalDate.now();

        // List all events
        List<Event> events = eventRepository.findAll();

        List<Event> allOngoingEvents =  events.stream()
                .filter(event -> event.getScheduledDate().isEqual(currentDate))
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
                .filter(event -> event.getScheduledDate().isAfter(currentDate))
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
                .filter(event -> event.getScheduledDate().isBefore(currentDate))
                .collect(Collectors.toList());

        // Check event availability
        if (allPastEvents.isEmpty()) {
            throw new RuntimeException("No past events found");
        }
        return allPastEvents;
    }

    private void validateDateAndTime(Event validateDate) {
        // Check the date is valid
        LocalDate currentDate = LocalDate.now();

        // Event should display to member before at least three days //
        LocalDate threeDaysBeforeEventDate = validateDate.getScheduledDate().minusDays(3);
        if(!currentDate.isBefore(threeDaysBeforeEventDate)){
            throw new RuntimeException("The event date is not at least three days before the current date");
        }

        // Set publish date and time without seconds
        LocalTime currentTime = LocalTime.now();
        LocalTime timeWithoutSeconds = currentTime.withNano(0);

        validateDate.setPublishedDate(currentDate);
        validateDate.setPublishedTime(timeWithoutSeconds);
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
        if (exisitingEvent.getScheduledDate() != null) {
            validateDateAndTime(event);
            exisitingEvent.setScheduledDate(event.getScheduledDate());
        }
        if (exisitingEvent.getEventImage() != null){
            exisitingEvent.setEventImage(event.getEventImage());
        }
        if (exisitingEvent.getDescription() != null){
            exisitingEvent.setDescription(event.getDescription());
        }
    }

    static void contentScheduleUpdating(ContentSchedule existingEvent, ContentSchedule contentSchedule) {

        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        LocalTime timeWithoutSeconds = currentTime.withNano(0);

        existingEvent.setPublishedDate(currentDate);
        existingEvent.setPublishedTime(timeWithoutSeconds);

        if (existingEvent.getDescription() != null) {
            existingEvent.setDescription(contentSchedule.getDescription());
        }
    }
}

