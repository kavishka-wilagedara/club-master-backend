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
    private String clubVision;
    private byte[] clubLogo;
    private byte[] backgroundImage1;
    private byte[] backgroundImage2;
    private byte[] backgroundImage3;

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

    public String getClubVision() {
        return clubVision;
    }

    public void setClubVision(String clubVision) {
        this.clubVision = clubVision;
    }

    public List<String> getAssociatedMembers() {
        return associatedMembers;
    }

    public void setAssociatedMembers(List<String> associatedMembers) {
        this.associatedMembers = associatedMembers;
    }

    public byte[] getClubLogo() {
        return clubLogo;
    }

    public void setClubLogo(byte[] clubLogo) {
        this.clubLogo = clubLogo;
    }

    public byte[] getBackgroundImage1() {
        return backgroundImage1;
    }

    public void setBackgroundImage1(byte[] backgroundImage1) {
        this.backgroundImage1 = backgroundImage1;
    }

    public byte[] getBackgroundImage2() {
        return backgroundImage2;
    }

    public void setBackgroundImage2(byte[] backgroundImage2) {
        this.backgroundImage2 = backgroundImage2;
    }

    public byte[] getBackgroundImage3() {
        return backgroundImage3;
    }

    public void setBackgroundImage3(byte[] backgroundImage3) {
        this.backgroundImage3 = backgroundImage3;
    }
}
