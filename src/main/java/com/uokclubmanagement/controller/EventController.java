package com.uokclubmanagement.controller;

import com.uokclubmanagement.entity.Event;
import com.uokclubmanagement.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/event")
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping("/save/{clubId}")
    public Event createEvent(@PathVariable("clubId") String clubId, @RequestBody Event event) {
        return eventService.createEvent(event, clubId);
    }

    @GetMapping("/all")
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }
}
