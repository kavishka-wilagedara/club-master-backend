package com.uokclubmanagement.service;

import com.uokclubmanagement.entity.Award;

import java.util.List;

public interface AwardService {

    Award createAward(String clubAdminId , String clubId,  Award award);
    List<Award> getAllAwards();
    List<Award> getAllAwardsByClubId(String clubId);
    Award updateAward(String clubAdminId , String awardId, Award award);
    void deleteAward(String awardId);
    Award getAwardById(String awardId);

}
