package com.uokclubmanagement.controller;

import com.uokclubmanagement.entity.Event;
import com.uokclubmanagement.repository.EventRepository;
import com.uokclubmanagement.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/event")
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping("/{clubId}/save/{clubAdminId}")
    public Event createEvent(@PathVariable("clubId") String clubId, @PathVariable("clubAdminId") String clubAdminId, @RequestBody Event event) {
        return eventService.createEvent(event, clubId, clubAdminId);
    }

    @GetMapping("/all")
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    @PutMapping("/{clubAdminId}/update/{eventId}")
    public Event updateEvent(@PathVariable("clubAdminId") String clubAdminId, @PathVariable("eventId") String eventId, @RequestBody Event event) {
        return eventService.updateEventById(clubAdminId, eventId, event);
    }

    @DeleteMapping("/delete/{eventId}")
    public void deleteEvent(@PathVariable("eventId") String eventId) {
        eventService.deleteEventById(eventId);
    }

    @GetMapping("/{clubId}/events")
    public List<Event> getEventsByClub(@PathVariable("clubId") String clubId) {
        return eventService.getAllEventsByClubId(clubId);
    }

    @GetMapping("/ongoing-events/{clubId}")
    public List<Event> ongoingEventsByClubId(@PathVariable("clubId") String clubId) {
        return eventService.getAllOngoingEventsByClubId(clubId);
    }

    @GetMapping("/upcoming-events/{clubId}")
    public List<Event> upcomingEventsByClubId(@PathVariable("clubId") String clubId) {
        return eventService.getAllUpcomingEventsByClubId(clubId);
    }

    @GetMapping("past-events/{clubId}")
    public List<Event> pastEventsByClubId(@PathVariable("clubId") String clubId) {
        return eventService.getAllPastEventsByClubId(clubId);
    }

    @GetMapping("/getEvent/{eventId}")
    public Event getEvent(@PathVariable("eventId") String eventId) {
        return eventService.getEventById(eventId);
    }

    @GetMapping("/all-ongoing-events")
    public List<Event> getAllOngoingEvents() {
        return eventService.getAllOngoingEvents();
    }

    @GetMapping("/all-upcoming-events")
    public List<Event> getAllUpcomingEvents() {
        return eventService.getAllUpcomingEvents();
    }

    @GetMapping("all-past-events")
    public List<Event> getAllPastEvents() {
        return eventService.getAllPastEvents();
    }

    @GetMapping("/all-ongoing-events/{memberId}")
    public List<Event> getAllOngoingEventsByMemberID(@PathVariable("memberId") String memberId){
        return eventService.getAllOngoingEventsByMemberId(memberId);
    }

    @GetMapping("/all-upcoming-events/{memberId}")
    public List<Event> getAllUpcomingEventsByMemberID(@PathVariable("memberId") String memberId){
        return eventService.getAllUpcomingEventsByMemberId(memberId);
    }

    @GetMapping("/all-past-events/{memberId}")
    public List<Event> getAllPastEventsByMemberID(@PathVariable("memberId") String memberId){
        return eventService.getAllPastEventsByMemberId(memberId);
    }
}
