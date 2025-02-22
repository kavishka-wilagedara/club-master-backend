package com.uokclubmanagement.controller;

import com.uokclubmanagement.entity.Award;
import com.uokclubmanagement.entity.News;
import com.uokclubmanagement.entity.Project;
import com.uokclubmanagement.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping("/{clubId}/save/{clubAdminId}")
    public Project createProject(@PathVariable("clubId") String clubId, @PathVariable("clubAdminId") String clubAdminId, @RequestBody Project project) {
        return projectService.createProject(clubAdminId, clubId, project);
    }

    @GetMapping("/all")
    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }

    @GetMapping("/{clubId}/getAllProjectsByClubId")
    public List<Project> getAllProjectsByClubId(@PathVariable String clubId) {
        return projectService.getAllProjectsByClubId(clubId);
    }

    @DeleteMapping("/{projectId}/deleteProject")
    public void deleteAward(@PathVariable String projectId) {
        projectService.deleteProject(projectId);
    }

    @PutMapping("/{clubAdminId}/update/{projectId}")
    public Project updateProject(@PathVariable String clubAdminId, @PathVariable String projectId, @RequestBody Project project) {
        return projectService.updateProject(clubAdminId, projectId, project);
    }

    @GetMapping("/findProjectById/{projectId}")
    public Project findProjectById(@PathVariable("projectId") String projectId) {
        return projectService.getProjectById(projectId);
    }
}
