package com.uokclubmanagement.service;

import com.uokclubmanagement.entity.MainAdmin;
import com.uokclubmanagement.repository.MainAdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MainAdminServiceImpl implements MainAdminService {

    @Autowired
    private MainAdminRepository mainAdminRepository;
    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @Override
    public MainAdmin createMainAdmin(MainAdmin mainAdmin) {

        if (mainAdmin.getMainAdminId()   == null || mainAdmin.getMainAdminId().isEmpty()){
            long seqValue = sequenceGeneratorService.generateSequence("Main Admin Sequence");
            String memberId = String.format("Adm-%04d", seqValue);
            mainAdmin.setMainAdminId(memberId);
        }

        return mainAdminRepository.save(mainAdmin);
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
        // Update the fields
        updateMainAdminFields(existingMainAdmin, mainAdmin);

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
