package com.uokclubmanagement.service;

import com.uokclubmanagement.entity.Club;

import java.util.List;

public interface ClubService {

    Club createClub(Club club);
    List<Club> getAllClubs();
    Club updateClubById(String clubId, Club club);
    void deleteClubById(String clubId);
    Club getClubById(String clubId);
}
