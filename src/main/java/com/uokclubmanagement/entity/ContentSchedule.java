package com.uokclubmanagement.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ContentSchedule {

    private LocalDate publishedDate;
    private LocalTime publishedTime;
    private LocalDate scheduledDate;
    private LocalTime scheduledTime;
    private String publisherName;
    private String responseClub;
    private String description;


    public ContentSchedule() {}

    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }

    public LocalTime getPublishedTime() {
        return publishedTime;
    }

    public void setPublishedTime(LocalTime publishedTime) {
        this.publishedTime = publishedTime.withNano(0);
    }

    public LocalDate getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(LocalDate scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public LocalTime getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(LocalTime scheduledTime) {
        this.scheduledTime = scheduledTime.withNano(0);
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public String getResponseClub() {
        return responseClub;
    }

    public void setResponseClub(String responseClub) {
        this.responseClub = responseClub;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
