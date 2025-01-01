package com.uokclubmanagement.repository;

import com.uokclubmanagement.entity.Club;
import com.uokclubmanagement.entity.Member;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ClubRepository extends MongoRepository<Club, String> {

    Club findClubByClubName(String clubName);
    Club findClubByClubAddress(String clubAddress);
    Club findClubByClubProducer(String clubProducer);
}
