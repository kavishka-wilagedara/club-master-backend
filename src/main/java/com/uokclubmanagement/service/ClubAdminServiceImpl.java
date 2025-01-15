package com.uokclubmanagement.service;

import com.uokclubmanagement.entity.Club;
import com.uokclubmanagement.entity.ClubAdmin;
import com.uokclubmanagement.entity.Member;
import com.uokclubmanagement.repository.ClubAdminRepository;
import com.uokclubmanagement.repository.ClubRepository;
import com.uokclubmanagement.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClubAdminServiceImpl implements ClubAdminService {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ClubRepository clubRepository;
    @Autowired
    private ClubAdminRepository clubAdminRepository;
    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @Override
    public ClubAdmin createClubAdmin(String memberId, String clubId, ClubAdmin clubAdmin) {

        // Check already registered as clubAdmin. one memberId can be a one club admin
        Optional<ClubAdmin> existClubAdmin = Optional.ofNullable(clubAdminRepository.findClubAdminByMemberId(memberId));
        if (existClubAdmin.isPresent()) {
            throw new RuntimeException("Club Admin already exists with clubId: " + existClubAdmin.get().getClubId());
        }

        // Find member and club are exist
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        Optional<Club> optionalClub = clubRepository.findById(clubId);

        if (optionalMember.isEmpty()) {
            throw new RuntimeException("Member not found with memberId " + memberId);
        }
        else if (optionalClub.isEmpty()) {
            throw new RuntimeException("Club not found with clubId " + clubId);
        }


        // Find club admin username exist
        Optional<ClubAdmin> optionalClubAdmin = Optional.ofNullable(clubAdminRepository.findClubAdminByUsername(clubAdmin.getUsername()));

        // Check club admin username with member username
        Member member = optionalMember.get();
        // Check the availability of clubId on member associated clubs
        List<String> memberAssociatedClub = member.getAssociatedClubs();

        if (optionalClubAdmin.isPresent()) {
            throw new RuntimeException("ClubAdmin username already exist: " + clubAdmin.getUsername());
        }

        else if(clubAdmin.getUsername().equals(member.getUserName())){
            throw new RuntimeException("ClubAdmin username can not as member: " + clubAdmin.getUsername());
        }

        else if(!memberAssociatedClub.contains(clubId)){
            throw new RuntimeException("Member is not associated with clubId: " + clubId);
        }

        // Create club admin
        else{
            if(clubAdmin.getClubAdminId() == null || clubAdmin.getClubAdminId().isEmpty()) {
                long seqValue = sequenceGeneratorService.generateSequence("Main Admin Sequence");
                String clubAdminId = String.format(clubId + ":" + "Admin-%03d", seqValue);

                clubAdmin.setClubId(clubId);
                clubAdmin.setClubAdminId(clubAdminId);
                clubAdmin.setMemberId(memberId);
                clubAdmin.setFullName(member.getFirstName()+" "+member.getLastName());
                clubAdmin.setClubAdminImage(member.getMemberImage());
            }
        }
        return clubAdminRepository.save(clubAdmin);
    }

    @Override
    public List<ClubAdmin> getAllClubAdmins() {
        return clubAdminRepository.findAll();
    }

    @Override
    public List<ClubAdmin> getAllClubAdminsByClubId(String clubId) {

        List<ClubAdmin> clubAdmins = new ArrayList<>();

        for (int i = 0; i < getAllClubAdmins().size(); i++) {
            if (getAllClubAdmins().get(i).getClubId().equals(clubId)) {
                clubAdmins.add(getAllClubAdmins().get(i));
            }
        }
        return clubAdmins;
    }

    @Override
    public Optional<ClubAdmin> getClubAdminById(String clubAdminId) {

        Optional<ClubAdmin> optionalClubAdmin = clubAdminRepository.findById(clubAdminId);

        if (optionalClubAdmin.isEmpty()){
            throw new RuntimeException("Club admin id not found with: "+clubAdminId);
        }

        return clubAdminRepository.findById(clubAdminId);
    }

    @Override
    public void deleteClubAdmin(String clubAdminId) {

        Optional<ClubAdmin> optionalClubAdmin = clubAdminRepository.findById(clubAdminId);

        if (optionalClubAdmin.isEmpty()){
            throw new RuntimeException("Club admin not found with: "+clubAdminId);
        }

        clubAdminRepository.delete(optionalClubAdmin.get());

    }
}

