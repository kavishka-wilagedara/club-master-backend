package com.uokclubmanagement.service;

import com.uokclubmanagement.entity.Club;
import com.uokclubmanagement.entity.MainAdmin;
import com.uokclubmanagement.entity.Member;
import com.uokclubmanagement.repository.ClubRepository;
import com.uokclubmanagement.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        Club existingClub = clubRepository.findClubByClubId(clubId);
        // Check if existingClub is null
        if (existingClub == null) {
            throw new RuntimeException("Club not found with id: " + clubId);
        }
        // Update the fields
        existingClub.setClubAddress(club.getClubAddress());
        existingClub.setClubSeniorAdviser(club.getClubSeniorAdviser());
        System.out.println("New Senior Adviser: " + existingClub.getClubSeniorAdviser());

        return clubRepository.save(existingClub);
    }

    @Override
    public Club deleteClubById(String clubId) {
        try {
            Club deleteClub = clubRepository.findClubByClubId(clubId);
            clubRepository.delete(deleteClub);
            System.out.println("Deleted Club: ");

            return deleteClub;
        }
        catch (Exception e) {
            throw new RuntimeException("Club not found with id: " + clubId);
        }
    }

    @Override
    public Club getClubByClubId(String clubId) {

        Club findClub = clubRepository.findClubByClubId(clubId);
        if (findClub == null) {
            throw new RuntimeException("Club not found with Id: " + clubId);
        }
        else {
            return findClub;
        }
    }

    @Override
    public Member enrollMemberToClub(String memberId, String clubId) {

        // Find member and club is exist
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        Optional<Club> optionalClub = clubRepository.findById(clubId);

        if (optionalMember.isPresent() && optionalClub.isPresent()) {
            Member member = optionalMember.get();
            Club club = optionalClub.get();

            // Update member and club
            member.getAssociatedClubs().add(clubId);
            club.getAssociatedMembers().add(memberId);

            memberRepository.save(member);
            clubRepository.save(club);

            return member;
        }

        else {
            throw new RuntimeException("Invalid memberId or ClubId.");
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
}
