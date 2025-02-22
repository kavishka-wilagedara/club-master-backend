package com.uokclubmanagement.service.impl;

import com.uokclubmanagement.entity.ClubAdmin;
import com.uokclubmanagement.entity.MainAdmin;
import com.uokclubmanagement.entity.Member;
import com.uokclubmanagement.dto.UserDTO;
import com.uokclubmanagement.repository.ClubAdminRepository;
import com.uokclubmanagement.repository.MainAdminRepository;
import com.uokclubmanagement.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl {

    @Autowired
    private MainAdminRepository mainAdminRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ClubAdminRepository clubAdminRepository;

    public UserDTO login(String username, String password) {

        // Check main-admin collection
        MainAdmin mainAdmin = mainAdminRepository.findMainAdminByMainAdminUsername(username);
        if (mainAdmin != null && mainAdmin.getMainAdminPassword().equals(password)) {
            return new UserDTO("MAIN_ADMIN", username, mainAdmin.getMainAdminId());
        }

        // Check club-admin collection
        ClubAdmin clubAdmin = clubAdminRepository.findClubAdminByUsername(username);
        if (clubAdmin != null && clubAdmin.getPassword().equals(password)){
            return new UserDTO("CLUB_ADMIN", username, clubAdmin.getClubAdminId());
        }

        // Check member collection
        Member member = memberRepository.findMemberByUserName(username);
        if (member != null && member.getPassword().equals(password)){
                return new UserDTO("MEMBER", username, member.getMemberId());
        }

        throw new RuntimeException("Invalid username or password");
    }
}
