package com.uokclubmanagement.service;

import com.uokclubmanagement.entity.Award;
import com.uokclubmanagement.entity.Club;
import com.uokclubmanagement.entity.ClubAdmin;
import com.uokclubmanagement.repository.AwardRepository;
import com.uokclubmanagement.repository.ClubAdminRepository;
import com.uokclubmanagement.repository.ClubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AwardServiceImpl implements AwardService {

    @Autowired
    private AwardRepository awardRepository;
    @Autowired
    private EventServiceImpl eventServiceImpl;
    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;
    @Autowired
    private ClubAdminRepository clubAdminRepository;
    @Autowired
    private ClubRepository clubRepository;

    @Override
    public Award createAward(String clubAdminId, String clubId, Award award) {

        // Validate clubAdminId and clubId
        ClubAdmin clubAdmin = eventServiceImpl.validateClubAdminAndClub(clubAdminId, clubId);

        // Check clubAdmin exist the clubId
        if (!clubAdmin.getClubId().equals(clubId)){
            throw new RuntimeException("Club ID does not match with Club Admin ID");
        }

        else {
            // Set the awardId
            if (award.getAwardId() == null || award.getAwardId().isEmpty()) {
                long seqValue = sequenceGeneratorService.generateSequence("Award Sequence");
                String awardId = String.format("Award-%04d", seqValue);
                award.setAwardId(awardId);
            }
            // Set awarded club
            award.setAwardedClub(clubId);

            // Check award date
            LocalDate currentDate = LocalDate.now();

            if(award.getAwardDate().isBefore(currentDate)){
                // Set award date
                award.setAwardDate(award.getAwardDate());
            }
            else {
                throw new RuntimeException("Invalid award date");
            }

            return awardRepository.save(award);
        }
    }

    @Override
    public List<Award> getAllAwards() {
        return awardRepository.findAll();
    }

    @Override
    public List<Award> getAllAwardsByClubId(String clubId) {

        Optional<Club> club = clubRepository.findById(clubId);

        if (club.isPresent()) {

            List<Award> awardsByClubId = awardRepository.findAllAwardsByAwardedClub(clubId);
            return awardsByClubId;
        }
        else {
            throw new RuntimeException("Invalid Club ID");
        }
    }

    @Override
    public Award updateAward(String clubAdminId, String awardId, Award award) {

        // Find award and clubAdmin are existing
        Optional<Award> awardById = awardRepository.findById(awardId);
        Optional<ClubAdmin> optionalClubAdmin = clubAdminRepository.findById(clubAdminId);

        if (awardById.isEmpty()){
            throw new RuntimeException("Invalid award ID");
        }

        else if (optionalClubAdmin.isEmpty()){
            throw new RuntimeException("Invalid club admin ID");
        }

        else if(!optionalClubAdmin.get().getClubId().equals(awardById.get().getAwardedClub())){
            throw new RuntimeException("Club ID does not match with Club Admin ID");
        }
        else {
            Award existingAward = awardById.get();

            // Set name and description
            existingAward.setAwardName(award.getAwardName());
            existingAward.setAwardDescription(award.getAwardDescription());

            // Check award date
            LocalDate currentDate = LocalDate.now();

            if(award.getAwardDate().isBefore(currentDate)){
                existingAward.setAwardDate(award.getAwardDate());
            }
            else {
                throw new RuntimeException("Invalid award date");
            }

            // Save updated fields on award collection
            return awardRepository.save(existingAward);
        }
    }

    @Override
    public void deleteAward(String awardId) {

        Optional<Award> awardById = awardRepository.findById(awardId);

        if (awardById.isPresent()) {
            awardRepository.delete(awardById.get());
        }
        else {
            throw new RuntimeException("Award does not exist");
        }
    }
}
