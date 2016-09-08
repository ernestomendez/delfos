package com.kadasoftware.delfos.service;

import com.kadasoftware.delfos.domain.Projects;
import com.kadasoftware.delfos.repository.ProjectsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Projects.
 */
@Service
public class ProjectsService {

    private final Logger log = LoggerFactory.getLogger(ProjectsService.class);
    
    @Inject
    private ProjectsRepository projectsRepository;

    /**
     * Save a projects.
     *
     * @param projects the entity to save
     * @return the persisted entity
     */
    public Projects save(Projects projects) {
        log.debug("Request to save Projects : {}", projects);
        Projects result = projectsRepository.save(projects);
        return result;
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
}
