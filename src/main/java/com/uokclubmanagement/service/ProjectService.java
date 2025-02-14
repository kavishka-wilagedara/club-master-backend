package com.uokclubmanagement.service;

import com.uokclubmanagement.entity.Award;
import com.uokclubmanagement.entity.Project;

import java.util.List;

public interface ProjectService {

    Project createProject(String clubAdminId , String clubId, Project project);
    List<Project> getAllProjects();
    List<Project> getAllProjectsByClubId(String clubId);
    Project updateProject(String clubAdminId , String projectId, Project project);
    void deleteProject(String projectId);
    Project getProjectById(String projectId);
}
