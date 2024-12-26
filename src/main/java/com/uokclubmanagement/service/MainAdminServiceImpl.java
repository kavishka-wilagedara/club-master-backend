package com.uokclubmanagement.service;

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
public class MainAdminServiceImpl implements MainAdminService {

    @Autowired
    private MainAdminRepository mainAdminRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @Override
    public MainAdmin createMainAdmin(MainAdmin mainAdmin) {

        // Check the member exist
        String memberId = mainAdmin.getMemberId();

        // Query the database to check if a member with the same memberId exists
        Optional<Member> existingMemberByMemberId = Optional.ofNullable(memberRepository.findMemberByMemberId(memberId));

        if (existingMemberByMemberId.isPresent()) {

            // Set adminId
            if (mainAdmin.getMainAdminId()   == null || mainAdmin.getMainAdminId().isEmpty()){
                long seqValue = sequenceGeneratorService.generateSequence("Main Admin Sequence");
                String mainAdminId = String.format("Adm-%04d", seqValue);

                // Set member's information into mainAdmin's information
                mainAdmin.setMainAdminId(mainAdminId);
                mainAdmin.setFirstName(existingMemberByMemberId.get().getFirstName());
                mainAdmin.setLastName(existingMemberByMemberId.get().getLastName());
                mainAdmin.setPhoneNo(existingMemberByMemberId.get().getPhoneNo());
                mainAdmin.setEmail(existingMemberByMemberId.get().getEmail());
                mainAdmin.setUserName(existingMemberByMemberId.get().getUserName());
                mainAdmin.setPassword(existingMemberByMemberId.get().getPassword());

                return mainAdminRepository.save(mainAdmin);
            }
        }

        // If member not exist
        else {
            throw new RuntimeException("Member Id not found with memberId: " + memberId);
        }
        return null;
    }

    @Override
    public List<MainAdmin> getAllMainAdmins() {
        return mainAdminRepository.findAll();
    }

    @Override
    public MainAdmin updateMainAdminById(String mainAdminID, MainAdmin mainAdmin) {

        MainAdmin existingMainAdmin = mainAdminRepository.findMainAdminByMainAdminId(mainAdminID);
        // Check if existingMainAdmin is null
        if (existingMainAdmin == null) {
            throw new RuntimeException("MainAdmin not found with id: " + mainAdminID);
        }
        // Update the mainAdmin fields
        updateMainAdminFields(existingMainAdmin, mainAdmin);

        // Check the mainAdmin is member
        Optional<Member> existingMemberByMemberId = Optional.ofNullable(memberRepository.findMemberByMemberId(mainAdmin.getMemberId()));
        if (existingMemberByMemberId.isPresent()) {

            // Update the member fields
            existingMemberByMemberId.get().setFirstName(mainAdmin.getFirstName());
            existingMemberByMemberId.get().setLastName(mainAdmin.getLastName());
            existingMemberByMemberId.get().setEmail(mainAdmin.getEmail());
            existingMemberByMemberId.get().setPhoneNo(mainAdmin.getPhoneNo());
            existingMemberByMemberId.get().setPassword(mainAdmin.getPassword());

        }
        // Save on member collection
        memberRepository.save(existingMainAdmin);
        // Save on mainAdmin collection
        return mainAdminRepository.save(existingMainAdmin);
    }

    private void updateMainAdminFields(MainAdmin existingMainAdmin, MainAdmin mainAdmin) {

        if (mainAdmin.getFirstName() != null) {
            existingMainAdmin.setFirstName(mainAdmin.getFirstName());
        }
        if (mainAdmin.getLastName() != null) {
            existingMainAdmin.setLastName(mainAdmin.getLastName());
        }
        if (mainAdmin.getEmail() != null) {
            existingMainAdmin.setEmail(mainAdmin.getEmail());
        }
        if (mainAdmin.getPhoneNo() != null) {
            existingMainAdmin.setPhoneNo(mainAdmin.getPhoneNo());
        }
        if (mainAdmin.getPassword() != null) {
            existingMainAdmin.setPassword(mainAdmin.getPassword());
        }
    }

    @Override
    public void deleteMainAdminById(String mainAdminId) {
        try {
            MainAdmin deleteMainAdmin = mainAdminRepository.findMainAdminByMainAdminId(mainAdminId);
            mainAdminRepository.delete(deleteMainAdmin);
        }
        catch (Exception e) {
            throw new RuntimeException("MainAdmin not found with id: " + mainAdminId);
        }
    }

    @Override
    public MainAdmin getMainAdminById(String mainAdminId) {

        MainAdmin findMainAdmin = mainAdminRepository.findMainAdminByMainAdminId(mainAdminId);
        if (findMainAdmin == null) {
            throw new RuntimeException("MainAdmin not found with id: " + mainAdminId);
        }
        else {
            return findMainAdmin;
        }
    }
}
