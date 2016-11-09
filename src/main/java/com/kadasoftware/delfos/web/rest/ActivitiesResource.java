package com.kadasoftware.delfos.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.kadasoftware.delfos.domain.Activities;
import com.kadasoftware.delfos.service.ActivitiesService;
import com.kadasoftware.delfos.service.dto.ActivitiesDTO;
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
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Activities.
 */
@RestController
@RequestMapping("/api")
public class ActivitiesResource {

    private final Logger log = LoggerFactory.getLogger(ActivitiesResource.class);

    @Inject
    private ActivitiesService activitiesService;

    /**
     * POST  /activities : Create a new activities.
     *
     * @param activities the activities to create
     * @return the ResponseEntity with status 201 (Created) and with body the new activities, or with status 400 (Bad Request) if the activities has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/activities",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Activities> createActivities(@Valid @RequestBody Activities activities) throws URISyntaxException {
        log.debug("REST request to save Activities : {}", activities);
        if (activities.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("activities", "idexists", "A new activities cannot already have an ID")).body(null);
        }
        Activities result = activitiesService.save(activities);
        return ResponseEntity.created(new URI("/api/activities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("activities", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /activities : Updates an existing activities.
     *
     * @param activities the activities to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated activities,
     * or with status 400 (Bad Request) if the activities is not valid,
     * or with status 500 (Internal Server Error) if the activities couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/activities",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Activities> updateActivities(@Valid @RequestBody Activities activities) throws URISyntaxException {
        log.debug("REST request to update Activities : {}", activities);
        if (activities.getId() == null) {
            return createActivities(activities);
        }
        Activities result = activitiesService.save(activities);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("activities", activities.getId().toString()))
            .body(result);
    }

    /**
     * GET  /activities : get all the activities.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of activities in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/activities",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Activities>> getAllActivities(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Activities");
        Page<Activities> page = activitiesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/activities");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /activities/:id : get the "id" activities.
     *
     * @param id the id of the activities to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the activities, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/activities/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Activities> getActivities(@PathVariable String id) {
        log.debug("REST request to get Activities : {}", id);
        Activities activities = activitiesService.findOne(id);
        return Optional.ofNullable(activities)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /activities/:id : delete the "id" activities.
     *
     * @param id the id of the activities to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/activities/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteActivities(@PathVariable String id) {
        log.debug("REST request to delete Activities : {}", id);
        activitiesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("activities", id.toString())).build();
    }

    @RequestMapping(value = "/project/{project}/sprint/{sprintId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Activities>> getAllActivitiesBySprint(@PathVariable String project, @PathVariable String sprintId) {
        log.debug("Request to get all activities by project and sprint");
        Assert.notNull(project, "Project can't be null");
        Assert.notNull(sprintId, "Sprint can't be null");

        List<Activities> activities = activitiesService.findAllByProjectAndSprintId(project, sprintId);

        return new ResponseEntity(activities, HttpStatus.OK);
    }

    @RequestMapping(value = "/{project}/{sprintId}/activities",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<ActivitiesDTO>> getActivitiesAndSubTasksBySprint(@PathVariable String project, @PathVariable String sprintId) {
        log.debug("Request to get all activities by project and sprint");
        Assert.notNull(project, "Project can't be null");
        Assert.notNull(sprintId, "Sprint can't be null");

        List<ActivitiesDTO> activities = activitiesService.findAllActivitiesAndSubTasksBySprint(project, sprintId);

        return new ResponseEntity(activities, HttpStatus.OK);
    }

}
