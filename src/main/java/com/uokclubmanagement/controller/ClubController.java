package com.uokclubmanagement.controller;

import com.uokclubmanagement.entity.Club;
import com.uokclubmanagement.entity.Member;
import com.uokclubmanagement.service.ClubService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/club")
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

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateClub(@PathVariable("id") String clubId, @RequestBody Club club) {
        try {
            clubService.updateClubById(clubId, club);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public void deleteClub(@PathVariable("id") String clubId) {
        clubService.deleteClubById(clubId);
    }

    @GetMapping("findClub/{id}")
    public Club findClubById(@PathVariable("id") String clubId) {
        return clubService.getClubById(clubId);
    }
}
