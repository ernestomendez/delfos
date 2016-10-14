package com.kadasoftware.delfos.service;

import com.kadasoftware.delfos.domain.Projects;
import com.kadasoftware.delfos.domain.User;
import com.kadasoftware.delfos.repository.ProjectsRepository;
import com.kadasoftware.delfos.service.dto.UserDTO;
import com.kadasoftware.delfos.service.dto.UserForProject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Service Implementation for managing Projects.
 */
@Service
public class ProjectsService {

    private final Logger log = LoggerFactory.getLogger(ProjectsService.class);

    @Inject
    private ProjectsRepository projectsRepository;

    @Inject
    private UserService userService;

    /**
     * Save a projects.
     *
     * @param projects the entity to save
     * @return the persisted entity
     */
    public Projects save(Projects projects) {
        log.debug("Request to save Projects : {}", projects);

        UserForProject user = new UserForProject(userService.getUserWithAuthorities());
        Set<UserForProject> userDTOSet = new HashSet<>(1);
        userDTOSet.add(user);
        projects.setUsers(userDTOSet);
        return projectsRepository.save(projects);
    }

    /**
     *  Get all the projects.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    public Page<Projects> findAll(Pageable pageable) {
        log.debug("Request to get all Projects");
        Page<Projects> result = projectsRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one projects by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    public Projects findOne(String id) {
        log.debug("Request to get Projects : {}", id);
        Projects projects = projectsRepository.findOne(id);
        return projects;
    }

    /**
     *  Delete the  projects by id.
     *
     *  @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Projects : {}", id);
        projectsRepository.delete(id);
    }

    /**
     * Finds all the projects where a user is assigned.
     *
     * @param login user to find.
     * @return list of projects.
     */
    public List<Projects> findAllByUser(String login) {
        log.debug("Request find Projects by login : {}", login);
        Assert.notNull(login, "login can not be null");
        final User user = userService.getUserWithAuthoritiesByLogin(login).get();
        Assert.notNull(user, "user can not be null");

        UserForProject userForProject = new UserForProject(user);

        return projectsRepository.findAllByUsersContaining(userForProject);
    }
}
