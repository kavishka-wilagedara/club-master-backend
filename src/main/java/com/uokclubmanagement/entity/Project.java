package com.uokclubmanagement.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Document("projects")
public class Project extends ContentSchedule {

    @Id
    private String projectId;

    private String projectName;
    private LocalDate projectHeldDate;

    private byte[] projectImage;

    public Project() {}

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public LocalDate getProjectHeldDate() {
        return projectHeldDate;
    }

    public void setProjectHeldDate(LocalDate projectHeldDate) {
        this.projectHeldDate = projectHeldDate;
    }

    public byte[] getProjectImage() {
        return projectImage;
    }

    public void setProjectImage(byte[] projectImage) {
        this.projectImage = projectImage;
    }
}
