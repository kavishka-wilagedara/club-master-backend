package com.uokclubmanagement.service;

import com.uokclubmanagement.entity.Club;
import com.uokclubmanagement.entity.MainAdmin;
import com.uokclubmanagement.entity.Member;
import com.uokclubmanagement.repository.ClubRepository;
import com.uokclubmanagement.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClubServiceImpl implements ClubService {

    @Autowired
    private ClubRepository clubRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @Override
    public Club createClub(Club club) {

        // Check the club exist
        Optional<Club> optionalClubByName = Optional.ofNullable(clubRepository.findClubByClubName(club.getClubName()));
        if (optionalClubByName.isPresent()) {
                throw new RuntimeException("A club with the same name already exists.");
        }

        Optional<Club> optionalClubByAddress = Optional.ofNullable(clubRepository.findClubByClubAddress(club.getClubAddress()));
        if (optionalClubByAddress.isPresent()) {
            throw new RuntimeException("A club with the same address already exists.");
        }

        Optional<Club> optionalClubByProducer = Optional.ofNullable(clubRepository.findClubByClubProducer(club.getClubProducer()));
        if (optionalClubByProducer.isPresent()) {
            throw new RuntimeException("A club with the same producer already exists.");
        }

        // If not exist
            if (club.getClubId() == null || club.getClubId().isEmpty()) {
                long seqValue = sequenceGeneratorService.generateSequence("Clubs Sequence");
                String clubId = String.format("Club-%03d", seqValue);
                club.setClubId(clubId);
            }

            return clubRepository.save(club);
    }

    @Override
    public List<Club> getAllClubs() {
        return clubRepository.findAll();
    }

    @Override
    public Club updateClubById(String clubId, Club club) {
        Optional<Club> existingClub = clubRepository.findById(clubId);
        // Check if existingClub is null
        if (existingClub.isEmpty()) {
            throw new RuntimeException("Club not found with id: " + clubId);
        }
        // Update the fields
        existingClub.get().setClubAddress(club.getClubAddress());
        existingClub.get().setClubSeniorAdviser(club.getClubSeniorAdviser());
        existingClub.get().setClubLogo(club.getClubLogo());
        existingClub.get().setBackgroundImage1(existingClub.get().getBackgroundImage1());
        existingClub.get().setBackgroundImage2(existingClub.get().getBackgroundImage2());
        existingClub.get().setBackgroundImage3(existingClub.get().getBackgroundImage3());
        System.out.println("New Senior Adviser: " + existingClub.get().getClubSeniorAdviser());

        return clubRepository.save(existingClub.get());
    }

    @Override
    public void deleteClubById(String clubId) {

        Optional<Club> deleteClub = clubRepository.findById(clubId);
            if (deleteClub.isPresent()) {
                clubRepository.delete(deleteClub.get());
                System.out.println("Deleted Club with id: " + clubId);
            }
        else{
            throw new RuntimeException("Club not found with id: " + clubId);
        }
    }

    @Override
    public Club getClubByClubId(String clubId) {

        Optional<Club> findClub = clubRepository.findById(clubId);
        if (findClub.isEmpty()) {
            throw new RuntimeException("Club not found with Id: " + clubId);
        }
        else {
            return findClub.get();
        }
    }

    @Override
    public Member enrollMemberToClub(String memberId, String clubId) {

        // Find member and club is exist
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        Optional<Club> optionalClub = clubRepository.findById(clubId);

        // Check already enrolled in to club
        if(optionalMember.isPresent() && optionalClub.isPresent()){
            Member member = optionalMember.get();
            List<String> memberAssociatedClubs = member.getAssociatedClubs();

            for (String id : memberAssociatedClubs) {
                if (id.equals(clubId)) {
                    throw new RuntimeException("Already enrolled with clubId: "+clubId);
                }
            }

            // If not enrolled in club
            Club club = optionalClub.get();

            // Update member and club
            member.getAssociatedClubs().add(clubId);
            club.getAssociatedMembers().add(memberId);

            memberRepository.save(member);
            clubRepository.save(club);

            return member;
        }
        else if (optionalMember.isEmpty()) {
            throw new RuntimeException("Invalid memberId.");
        }
        else {
            throw new RuntimeException("Invalid clubId.");
        }
    }

    @Override
    public Member unrollMemberFromClub(String memberId, String clubId) {

        // Find member and club is exist
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        Optional<Club> optionalClub = clubRepository.findById(clubId);

        if (optionalMember.isPresent() && optionalClub.isPresent()) {
            Member member = optionalMember.get();
            Club club = optionalClub.get();

            // Update member and club
            member.getAssociatedClubs().remove(clubId);
            club.getAssociatedMembers().remove(memberId);

            memberRepository.save(member);
            clubRepository.save(club);

            return member;
        }
        else if (optionalMember.isEmpty()) {
            throw new RuntimeException("Invalid memberId.");
        }
        else {
            throw new RuntimeException("Invalid clubId.");
        }
    }

    @Override
    public List<String> getClubsByMemberId(String memberId) {

        Optional<Member> optionalMember = memberRepository.findById(memberId);
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();

            // Get clubs in to List
            List<String> memberAssociatedClubs = member.getAssociatedClubs();
            List<String> memberAssociatedClubsName = new ArrayList<String>();

            // update club names in to List
            for (int i = 0; i < memberAssociatedClubs.size(); i++) {
                Optional<Club> optionalClub = clubRepository.findById(memberAssociatedClubs.get(i));
                if (optionalClub.isPresent()) {
                    Club club = optionalClub.get();
                    club.getClubName();
                    memberAssociatedClubsName.add(club.getClubName());
                }
            }
            return memberAssociatedClubsName;
        }
        else {
            throw new RuntimeException("Member not found with id: " + memberId);
        }
    }
}
