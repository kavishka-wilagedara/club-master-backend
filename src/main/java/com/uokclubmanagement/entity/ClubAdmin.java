package com.uokclubmanagement.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("club-admin")
public class ClubAdmin{

    @Id
    private String clubAdminId;

    private String clubId;
    private String memberId;
    private String fullName;
    private String email;
    @Indexed(unique = true)
    private String username;
    private String password;
    private byte[] clubAdminImage;

    public ClubAdmin() {}

    public String getClubAdminId() {
        return clubAdminId;
    }

    public void setClubAdminId(String clubAdminId) {
        this.clubAdminId = clubAdminId;
    }

    public String getClubId() {
        return clubId;
    }

    public void setClubId(String clubId) {
        this.clubId = clubId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getClubAdminImage() {
        return clubAdminImage;
    }

    public void setClubAdminImage(byte[] clubAdminImage) {
        this.clubAdminImage = clubAdminImage;
    }
}
