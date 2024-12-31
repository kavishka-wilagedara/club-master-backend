package com.uokclubmanagement.repository;

import com.uokclubmanagement.entity.Event;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EventRepository extends MongoRepository<Event, String> {

}
