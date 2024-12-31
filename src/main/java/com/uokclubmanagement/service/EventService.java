package com.uokclubmanagement.service;

import com.uokclubmanagement.entity.Event;

import java.util.Date;
import java.util.List;

public interface EventService {

    Event createEvent(Event event, String clubId);
    List<Event> getAllEvents();
    Event updateEventById(String eventId, Event event);
    void deleteEventById(String eventId);
    List<Event> getAllEventsByOrganizingClub(String clubId);
    List<Event> getAllOngoingEventsByOrganizingClub(String clubId);
    List<Event> getAllUpcomingEventsByOrganizingClub(String clubId);
}
