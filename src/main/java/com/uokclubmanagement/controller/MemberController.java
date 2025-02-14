package com.uokclubmanagement.controller;

import com.uokclubmanagement.entity.Club;
import com.uokclubmanagement.entity.Member;
import com.uokclubmanagement.repository.ClubRepository;
import com.uokclubmanagement.repository.MemberRepository;
import com.uokclubmanagement.service.ClubService;
import com.uokclubmanagement.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/member")
@CrossOrigin(origins = "http://localhost:5173")
public class MemberController {

    @Autowired
    private MemberService memberService;
    @Autowired
    private ClubService clubService;
    @Autowired
    private ClubRepository  clubRepository;
    @Autowired
    private MemberRepository MemberRepository;
    @Autowired
    private MemberRepository memberRepository;

    @PostMapping("/save")
    public Member save(@RequestBody Member member) {
            return memberService.createMember(member);
    }

    @GetMapping("/all")
    public List<Member> getAll() {
        return memberService.getAllMembers();
    }

    @PutMapping("/update/{memberId}")
    public ResponseEntity<?> updateMember(@PathVariable("memberId") String memberId, @RequestBody Member member) {
        try {
            memberService.updateMemberById(memberId, member);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{memberId}")
    public void deleteMember(@PathVariable("memberId") String memberId) {
        memberService.deleteMemberById(memberId);
    }

    @GetMapping("/getMember-memberId/{memberId}")
    public Member getMemberByMemberId(@PathVariable("memberId") String memberId) {
        return memberService.getMemberById(memberId);
    }

    @GetMapping("getMember-username/{userName}")
    public Member getMemberByUserName(@PathVariable("userName") String userName) {
        return memberService.getMemberByUserName(userName);
    }

    @PostMapping("login")
    public ResponseEntity<String> loginMember(@RequestBody Member loginMember) {
        Member member = memberService.getMemberByUserName(loginMember.getUserName());
        if(member.getPassword().equals(loginMember.getPassword())) {
            return ResponseEntity.ok("Login successful");
        }
        else
            throw new RuntimeException("Login failed");
    }
}

