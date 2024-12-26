package com.uokclubmanagement.service;

import com.uokclubmanagement.entity.Club;
import com.uokclubmanagement.entity.MainAdmin;
import com.uokclubmanagement.entity.Member;
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
    private MainAdminRepository mainAdminRepository;
    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @Override
    public Member createMember(Member member) {

        // Check the username already exist
        String userName = member.getUserName();

        // Query the database to check if a member with the same username exists
        Optional<Member> existingMemberByUserName = Optional.ofNullable(memberRepository.findMemberByUserName(userName));

        if (existingMemberByUserName.isPresent()) {
            throw new RuntimeException("Username already exist");
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
        Member findMember = memberRepository.findMemberByMemberId(memberId);
        if (findMember == null) {
            throw new RuntimeException("Member not found with memberId: " + memberId);
        }
        return findMember;
    }

    @Override
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    @Override
    public Member updateMemberById(String memberId, Member member) {

        Member existingMember = memberRepository.findMemberByMemberId(memberId);

        // Check the existingMember is null
        if (existingMember == null) {
            throw new RuntimeException("MainAdmin not found with id: " + memberId);
        }

        // Check the existingMember is mainAdmin
        Optional<MainAdmin> existingMainAdmin = Optional.ofNullable(mainAdminRepository.findMainAdminByMemberId(memberId));
        if(existingMainAdmin.isPresent()){
            // As a mainAdmin cant update profile
            throw new RuntimeException("You cant update at this moment, you have already registered as a mainAdmin." +
                    "Please log as a MainAdmin to update your profile ");
        }
        // Update the fields
        else {
            updateMemberFields(existingMember, member);
        }
        return memberRepository.save(existingMember);

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
    }

    @Override
    public void deleteMemberById(String memberId) {
        try {
            Member deleteMember = memberRepository.findMemberByMemberId(memberId);
            memberRepository.delete(deleteMember);
            System.out.println("Deleted Member: " + deleteMember.getMemberId());
        }
        catch (Exception e) {
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
