package project.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import project.dto.project.ProjectRequestCreateDto;
import project.dto.project.ProjectResponseDto;
import project.model.User;
import project.service.ProjectService;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
@Tag(name = "Project management.", description = "Endpoints for project management.")
public class ProjectController {
    private final ProjectService projectService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create new project for role manager.",
            description = "Create new project.")
    public ProjectResponseDto createProject(
            @RequestBody @Valid ProjectRequestCreateDto createProjectDto) {
        return projectService.createNewProject(createProjectDto);
    }

    @GetMapping("/manager")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @Operation(summary = "Find all projects for role manager.",
            description = "Find all projects.")
    public Page<ProjectResponseDto> retrieveManagerProjects(Pageable pageable) {
        return projectService.findAllProject(pageable);
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "Find all projects for role user.",
            description = "Find all projects which has task where login user is assignee.")
    public Page<ProjectResponseDto> retrieveUserProjects(Pageable pageable) {
        User user = getUserFromContext();
        return projectService.findUsersProjects(user, pageable);
    }

    @GetMapping("/{projectId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "Find project by id for role user.",
            description = "Find project by id which has task where login user is assignee.")
    public ProjectResponseDto retrieveProjectDetails(@PathVariable Long projectId) {
        User user = getUserFromContext();
        return projectService.findProjectDetails(user, projectId);
    }

    @GetMapping("/manager/{projectId}")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @Operation(summary = "Find project by id for role manager.",
            description = "Find any project by id.")
    public ProjectResponseDto retrieveManagerProjectDetails(@PathVariable Long projectId) {
        return projectService.findAnyProjectDetails(projectId);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @Operation(summary = "Update project by id for role manager.",
            description = "Update any project by id.")
    public ProjectResponseDto updateProject(
            @PathVariable Long id,
            @RequestBody @Valid ProjectRequestCreateDto createProjectDto) {
        return projectService.updateProject(id, createProjectDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Delete project by id for role admin.",
            description = "Delete any project by id.")
    public void deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
    }

    private User getUserFromContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }
}
