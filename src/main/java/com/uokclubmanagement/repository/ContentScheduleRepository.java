package com.uokclubmanagement.repository;

import com.uokclubmanagement.entity.ContentSchedule;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ContentScheduleRepository extends MongoRepository<ContentSchedule, String> {
}
