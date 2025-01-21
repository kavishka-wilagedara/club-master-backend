package com.uokclubmanagement.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Document("award")
public class Award {

    @Id
    private String awardId;

    private String awardName;
    private String awardDescription;
    private LocalDate awardDate;
    private String awardedClub;

    private byte[] awardedImage;

    public Award(){}

    public String getAwardId() {
        return awardId;
    }

    public void setAwardId(String awardId) {
        this.awardId = awardId;
    }

    public String getAwardName() {
        return awardName;
    }

    public void setAwardName(String awardName) {
        this.awardName = awardName;
    }

    public String getAwardDescription() {
        return awardDescription;
    }

    public void setAwardDescription(String awardDescription) {
        this.awardDescription = awardDescription;
    }

    public LocalDate getAwardDate() {
        return awardDate;
    }

    public void setAwardDate(LocalDate awardDate) {
        this.awardDate = awardDate;
    }

    public String getAwardedClub() {
        return awardedClub;
    }

    public void setAwardedClub(String awardedClub) {
        this.awardedClub = awardedClub;
    }

    public byte[] getAwardedImage() {
        return awardedImage;
    }

    public void setAwardedImage(byte[] awardedImage) {
        this.awardedImage = awardedImage;
    }
}
