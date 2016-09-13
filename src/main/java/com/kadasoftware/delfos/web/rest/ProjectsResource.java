package com.kadasoftware.delfos.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.kadasoftware.delfos.domain.Projects;
import com.kadasoftware.delfos.service.ProjectsService;
import com.kadasoftware.delfos.web.rest.util.HeaderUtil;
import com.kadasoftware.delfos.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Projects.
 */
@RestController
@RequestMapping("/api")
public class ProjectsResource {

    private final Logger log = LoggerFactory.getLogger(ProjectsResource.class);
        
    @Inject
    private ProjectsService projectsService;

    /**
     * POST  /projects : Create a new projects.
     *
     * @param projects the projects to create
     * @return the ResponseEntity with status 201 (Created) and with body the new projects, or with status 400 (Bad Request) if the projects has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/projects",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Projects> createProjects(@Valid @RequestBody Projects projects) throws URISyntaxException {
        log.debug("REST request to save Projects : {}", projects);
        if (projects.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("projects", "idexists", "A new projects cannot already have an ID")).body(null);
        }
        Projects result = projectsService.save(projects);
        return ResponseEntity.created(new URI("/api/projects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("projects", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /projects : Updates an existing projects.
     *
     * @param projects the projects to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated projects,
     * or with status 400 (Bad Request) if the projects is not valid,
     * or with status 500 (Internal Server Error) if the projects couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/projects",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Projects> updateProjects(@Valid @RequestBody Projects projects) throws URISyntaxException {
        log.debug("REST request to update Projects : {}", projects);
        if (projects.getId() == null) {
            return createProjects(projects);
        }
        Projects result = projectsService.save(projects);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("projects", projects.getId().toString()))
            .body(result);
    }

    /**
     * GET  /projects : get all the projects.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of projects in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/projects",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Projects>> getAllProjects(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Projects");
        Page<Projects> page = projectsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/projects");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /projects/:id : get the "id" projects.
     *
     * @param id the id of the projects to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the projects, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/projects/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Projects> getProjects(@PathVariable String id) {
        log.debug("REST request to get Projects : {}", id);
        Projects projects = projectsService.findOne(id);
        return Optional.ofNullable(projects)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /projects/:id : delete the "id" projects.
     *
     * @param id the id of the projects to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/projects/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteProjects(@PathVariable String id) {
        log.debug("REST request to delete Projects : {}", id);
        projectsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("projects", id.toString())).build();
    }

}
