package com.uokclubmanagement.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Document("news")
public class News extends ContentSchedule{

    @Id
    private String newsId;

    private String newsTitle;
    private List<String> membersLike =new ArrayList<>();
    private List<String> membersDislike =new ArrayList<>();

    public News() {}

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public List<String> getMembersLike() {
        return membersLike;
    }

    public void setMembersLike(List<String> membersLike) {
        this.membersLike = membersLike;
    }

    public List<String> getMembersDislike() {
        return membersDislike;
    }

    public void setMembersDislike(List<String> membersDislike) {
        this.membersDislike = membersDislike;
    }
}
