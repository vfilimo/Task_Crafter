package project.demo.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import project.demo.dto.project.ProjectRequestCreateDto;
import project.demo.dto.project.ProjectResponseDto;
import project.demo.exception.EntityNotFoundException;
import project.demo.mapper.ProjectMapper;
import project.demo.model.Project;
import project.demo.model.User;
import project.demo.repository.ProjectRepository;
import project.demo.service.ProjectService;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    @Override
    public ProjectResponseDto createNewProject(ProjectRequestCreateDto createProjectDto) {
        Project project = projectMapper.toEntity(createProjectDto);
        return projectMapper.toDto(projectRepository.save(project));
    }

    @Override
    public List<ProjectResponseDto> findUsersProjects(User user, Pageable pageable) {
        Page<Project> allProjectsForUser = projectRepository
                .findAllProjectsForUser(user.getId(), pageable);
        return projectMapper.toDto(allProjectsForUser);
    }

    @Override
    public ProjectResponseDto findProjectDetails(User user, Long projectId) {
        Project project = projectRepository.findProjectByUserIdAndProjectId(user.getId(), projectId)
                .orElseThrow(() -> new EntityNotFoundException(String.format(
                        "User with username: %s doesn't have project with id: %d",
                        user.getUsername(), projectId)));
        return projectMapper.toDto(project);
    }

    @Override
    public ProjectResponseDto updateProject(Long projectId,
                                            ProjectRequestCreateDto projectRequestCreateDto) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Can't find project with id: %d", projectId)));
        projectMapper.updateProject(project, projectRequestCreateDto);
        return projectMapper.toDto(projectRepository.save(project));
    }

    @Override
    public void deleteProject(Long projectId) {
        projectRepository.deleteById(projectId);
    }
}