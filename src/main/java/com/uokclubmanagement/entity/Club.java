package com.uokclubmanagement.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Document("clubs")
public class Club {

    @Id
    private String clubId;
    @Indexed(unique = true)
    private String clubName;
    private String clubAddress;
    @Indexed(unique = true)
    private String clubSeniorAdviser;
    @Indexed(unique = true)
    private String clubProducer;

    private List<String> associatedMembers = new ArrayList<>();

    public Club() {
    }

    public String getClubId() {
        return clubId;
    }

    public void setClubId(String clubId) {
        this.clubId = clubId;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getClubAddress() {
        return clubAddress;
    }

    public void setClubAddress(String clubAddress) {
        this.clubAddress = clubAddress;
    }

    public String getClubSeniorAdviser() {
        return clubSeniorAdviser;
    }

    public void setClubSeniorAdviser(String clubSeniorAdviser) {
        this.clubSeniorAdviser = clubSeniorAdviser;
    }

    public String getClubProducer() {
        return clubProducer;
    }

    public void setClubProducer(String clubProducer) {
        this.clubProducer = clubProducer;
    }

    public List<String> getAssociatedMembers() {
        return associatedMembers;
    }

    public void setAssociatedMembers(List<String> associatedMembers) {
        this.associatedMembers = associatedMembers;
    }
}
