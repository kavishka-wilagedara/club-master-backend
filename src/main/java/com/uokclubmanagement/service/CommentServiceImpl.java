package com.uokclubmanagement.service;

import com.uokclubmanagement.entity.Comment;
import com.uokclubmanagement.entity.Event;
import com.uokclubmanagement.entity.Member;
import com.uokclubmanagement.repository.CommentRepository;
import com.uokclubmanagement.repository.EventRepository;
import com.uokclubmanagement.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CommentServiceImpl implements CommentService{

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private LikeServiceImpl likeServiceImpl;
    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private EventRepository eventRepository;

    @Override
    public Event addCommentToEvent(String eventId, String clubId, String memberId, Comment comment) {
        // Validate event using method of likeServiceImpl class
        Event event = likeServiceImpl.validateClubIdWithEventAndMembers(eventId, clubId, memberId);

        // Create comment ID
        if (comment.getCommentId() == null || comment.getCommentId().isEmpty()) {
            long seqValue = sequenceGeneratorService.generateSequence("Comment Sequence");
            String commentId = String.format(eventId+":comment-%04d", seqValue);
            // Set commentId and other fields
            comment.setCommentId(commentId);
            comment.setEventId(eventId);
            comment.setMemberId(memberId);
            comment.setClubId(clubId);

            // Set member name
            Optional<Member> optionalMember = memberRepository.findById(memberId);
            if (optionalMember.isPresent()) {
                Member member = optionalMember.get();
                comment.setMemberName(member.getFirstName()+" "+member.getLastName());
            }
            commentRepository.save(comment);
            // Save comments on event field
            List<String> commentSection = event.getComments();
            commentSection.add(comment.getComment());
            event.setComments(commentSection);
        }
        eventRepository.save(event);
        return event;
    }

    @Override
    public void deleteCommentFromEvent(String commentId, String eventId, String clubId, String memberId) {
        // Validate event using method of likeServiceImpl class
        Event event = likeServiceImpl.validateClubIdWithEventAndMembers(eventId, clubId, memberId);

        // Find comment by commentId
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        if (optionalComment.isEmpty()) {
            throw new RuntimeException("Comment not found");
        }

        Comment comment = optionalComment.get();

        // Check comment exist eventId and memberId
        if (comment.getEventId().equals(eventId) && comment.getMemberId().equals(memberId)) {
            commentRepository.delete(comment);
        }

    }

    @Override
    public List<Comment> getAllEventCommentsByMemberId(String eventId, String memberId) {

        List<Comment> comments = commentRepository.findByMemberIdAndEventId(memberId, eventId);
        return comments;
    }

    @Override
    public List<Comment> getAllEventCommentsByEventId(String eventId) {

        List<Comment> comments = commentRepository.findByEventId(eventId);
        return comments;
    }

    @Override
    public Comment updateCommentInEventId(String commentId, String memberId, Comment comment) {

        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        if (optionalComment.isPresent()) {
            Comment existingComment = optionalComment.get();

            if(existingComment.getMemberId().equals(memberId)){
                existingComment.setComment(comment.getComment());
                return commentRepository.save(existingComment);
            }
            else {
                throw new RuntimeException("Member Id not match");
            }
        }
        else{
            throw new RuntimeException("Comment not found");
        }
    }
}
