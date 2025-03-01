package com.uokclubmanagement.controller;

import com.uokclubmanagement.dto.EnrollmentDTO;
import com.uokclubmanagement.entity.Club;
import com.uokclubmanagement.entity.Member;
import com.uokclubmanagement.service.ClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v1/club")
@CrossOrigin(origins = "http://localhost:5173")
public class ClubController {

    @Autowired
    private ClubService clubService;

    @PostMapping("/save")
    public Club saveClub(@RequestBody Club club) {
        return clubService.createClub(club);
    }

    @GetMapping("/all")
    public List<Club> getAllClubs() {
        return clubService.getAllClubs();
    }

    @PutMapping("/update/{clubId}")
    public Club updateClub(@PathVariable String clubId, @RequestBody Club club) {
        return clubService.updateClubById(clubId, club);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteClub(@PathVariable("id") String clubId) {
        clubService.deleteClubById(clubId);
    }

    @GetMapping("/findClub/{id}")
    public Club findClubById(@PathVariable("id") String clubId) {
        return clubService.getClubByClubId(clubId);
    }

    @PostMapping("/{memberId}/enroll-member/{clubId}")
    public Member assignMember(@PathVariable String memberId, @PathVariable String clubId, @RequestBody EnrollmentDTO enrollmentKey) {
        return clubService.enrollMemberToClub(memberId, clubId, enrollmentKey);
    }

    @DeleteMapping("/{memberId}/unroll-member/{clubId}")
    public Member unassignMember(@PathVariable String memberId, @PathVariable String clubId) {
        return clubService.unrollMemberFromClub(memberId, clubId);
    }

    @GetMapping("/{memberId}/getClubs")
    public List<Club> getClubsByMemberId(@PathVariable String memberId) {
        return clubService.getClubsByMemberId(memberId);
    }
}
