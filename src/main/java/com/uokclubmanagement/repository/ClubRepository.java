package com.uokclubmanagement.repository;

import com.uokclubmanagement.entity.Club;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClubRepository extends MongoRepository<Club, String> {

    Club findClubByClubId(String clubId);
    Club findByClubName(String clubName);
    Club findByClubAddress(String clubAddress);
    Club findByClubProducer(String clubProducer);
}
