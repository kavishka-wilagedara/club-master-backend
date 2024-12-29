package com.uokclubmanagement.service;

import com.uokclubmanagement.entity.Club;
import com.uokclubmanagement.entity.Member;

import java.util.List;

public interface ClubService {

    Club createClub(Club club);
    List<Club> getAllClubs();
    Club updateClubById(String clubId, Club club);
    Club deleteClubById(String clubId);
    Club getClubByClubId(String clubId);
    Member enrollMemberToClub(String memberId, String clubId);
    Member unrollMemberFromClub(String memberId, String clubId);
}
