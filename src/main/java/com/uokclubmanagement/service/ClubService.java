package com.uokclubmanagement.service;

import com.uokclubmanagement.dto.EnrollmentDTO;
import com.uokclubmanagement.entity.Club;
import com.uokclubmanagement.entity.Member;

import java.util.HashMap;
import java.util.List;

public interface ClubService {

    Club createClub(Club club);
    List<Club> getAllClubs();
    Club updateClubById(String clubId, Club club);
    void deleteClubById(String clubId);
    Club getClubByClubId(String clubId);
    Member enrollMemberToClub(String memberId, String clubId, EnrollmentDTO enrollmentKey);
    Member unrollMemberFromClub(String memberId, String clubId);

    List<Club> getClubsByMemberId(String memberId);
}
