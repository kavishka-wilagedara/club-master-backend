package com.uokclubmanagement.service;

import com.uokclubmanagement.entity.Club;
import com.uokclubmanagement.entity.ClubAdmin;
import com.uokclubmanagement.entity.MainAdmin;
import com.uokclubmanagement.entity.Member;
import com.uokclubmanagement.repository.ClubAdminRepository;
import com.uokclubmanagement.repository.ClubRepository;
import com.uokclubmanagement.repository.MainAdminRepository;
import com.uokclubmanagement.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ClubAdminRepository clubAdminRepository;
    @Autowired
    private MainAdminRepository mainAdminRepository;
    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @Override
    public Member createMember(Member member) {

        // Check the username and email already exist
        String userName = member.getUserName();
        String email = member.getEmail();

        // Query the database to check if a user with the same username and email exists
        Optional<Member> existingMemberByUserName = Optional.ofNullable(memberRepository.findMemberByUserName(userName));
        Optional<Member> existingMemberByEmail = Optional.ofNullable(memberRepository.findMemberByEmail(email));
        Optional<MainAdmin> existingMainAdminByUserName = Optional.ofNullable(mainAdminRepository.findMainAdminByMainAdminUsername(userName));
        Optional<MainAdmin> existingMainAdminByEmail = Optional.ofNullable(mainAdminRepository.findMainAdminByMainAdminEmail(email));
        Optional<ClubAdmin> existingClubAdminByUserName = Optional.ofNullable(clubAdminRepository.findClubAdminByUsername(userName));


        if (existingMemberByUserName.isPresent() || existingMainAdminByUserName.isPresent() || existingClubAdminByUserName.isPresent()) {
            throw new RuntimeException("Username already exist");
        }
        else if (existingMemberByEmail.isPresent() || existingMainAdminByEmail.isPresent()) {
            throw new RuntimeException("Email already exist");
        }

        // If not exist
        else{
        if (member.getMemberId()   == null || member.getMemberId().isEmpty()){
            long seqValue = sequenceGeneratorService.generateSequence("Member Sequence");
            String memberId = String.format("Mem-%05d", seqValue);
            member.setMemberId(memberId);
            }
            return memberRepository.save(member);
        }
    }

    @Override
    public Member getMemberById(String memberId) {
        Optional<Member> findMember = memberRepository.findById(memberId);
        if (findMember.isEmpty()) {
            throw new RuntimeException("Member not found with memberId: " + memberId);
        }
        return findMember.get();
    }

    @Override
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    @Override
    public Member updateMemberById(String memberId, Member member) {

        Optional<Member> existingMember = memberRepository.findById(memberId);

        // Check the existingMember is null
        if (existingMember.isEmpty()) {
            throw new RuntimeException("MainAdmin not found with id: " + memberId);
        }

        // Update the fields
        updateMemberFields(existingMember.get(), member);

        // Check if member is clubAdmin
        Optional<ClubAdmin> optionalClubAdmin = Optional.ofNullable(clubAdminRepository.findClubAdminByMemberId(memberId));
        if (optionalClubAdmin.isPresent()) {
            ClubAdmin clubAdmin = optionalClubAdmin.get();
            // Update clubAdmin fullName
            clubAdmin.setFullName(member.getFirstName()+" "+member.getLastName());
            clubAdminRepository.save(clubAdmin);
        }

        return memberRepository.save(existingMember.get());

    }

    private void updateMemberFields(Member existingMember, Member member) {
        if (member.getFirstName() != null) {
            existingMember.setFirstName(member.getFirstName());
        }
        if (member.getLastName() != null) {
            existingMember.setLastName(member.getLastName());
        }
        if (member.getEmail() != null) {
            existingMember.setEmail(member.getEmail());
        }
        if (member.getPhoneNo() != null) {
            existingMember.setPhoneNo(member.getPhoneNo());
        }
        if (member.getPassword() != null) {
            existingMember.setPassword(member.getPassword());
        }
//        if(member.getMemberImage() != null){
//            existingMember.setMemberImage(member.getMemberImage());
//        }
    }

    @Override
    public void deleteMemberById(String memberId) {
        Optional<Member> deleteMember = memberRepository.findById(memberId);
        if (deleteMember.isPresent()) {
            memberRepository.delete(deleteMember.get());
            System.out.println("Deleted member with id: " + memberId);
        }
        else {
            throw new RuntimeException("Member not found with id: " + memberId);
        }
    }

    @Override
    public Member getMemberByUserName(String userName) {
        Member findMember = memberRepository.findMemberByUserName(userName);
        if (findMember == null) {
            throw new RuntimeException("Member not found with username: " + userName);
        }
        return findMember;
    }
}
