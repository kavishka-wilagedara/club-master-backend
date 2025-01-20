package com.uokclubmanagement.controller;

import com.uokclubmanagement.entity.Award;
import com.uokclubmanagement.service.AwardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/award")
public class AwardController {

    @Autowired
    private AwardService awardService;

    @PostMapping("/{clubId}/save/{clubAdminId}")
    public Award saveAward(@PathVariable String clubId, @PathVariable String clubAdminId, @RequestBody Award award) {
        return awardService.createAward(clubAdminId, clubId, award);
    }

    @GetMapping("/all")
    public List<Award> getAllAwards() {
        return awardService.getAllAwards();
    }

    @GetMapping("/{clubId}/getAllAwardsByClubId")
    public List<Award> getAllAwardsByClubId(@PathVariable String clubId) {
        return awardService.getAllAwardsByClubId(clubId);
    }

    @DeleteMapping("/{awardId}/deleteAward")
    public void deleteAward(@PathVariable String awardId) {
        awardService.deleteAward(awardId);
    }

    @PutMapping("/{clubAdminId}/update/{awardId}")
    public Award updateAward(@PathVariable String clubAdminId, @PathVariable String awardId, @RequestBody Award award) {
        return awardService.updateAward(clubAdminId, awardId, award);
    }
}
