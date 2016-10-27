package com.kadasoftware.delfos.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.kadasoftware.delfos.domain.Activities;
import com.kadasoftware.delfos.service.ActivitiesService;
import com.kadasoftware.delfos.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URISyntaxException;

/**
 * Created by ernesto on 19/10/16.
 *
 * REST Controller for Backlog assign stories.
 */
@RestController
@RequestMapping("/api")
public class BackogAssignStoriesResource {

    private final Logger log = LoggerFactory.getLogger(BackogAssignStoriesResource.class);

    @Inject
    private TaskService taskService;

    @Inject
    private ActivitiesService activitiesService;

    /**
     * Assign a user story from backlog to a sprint, this includes subtasks creation.
     * Using the default work flow.
     *
     * @param activity the actual activity to assign.
     * @return the assigned activity.
     * @throws URISyntaxException
     */
    @RequestMapping(value = "/assign/stories",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Activities> assignActivityFromBacklog(@Valid @RequestBody Activities activity) throws URISyntaxException {
        log.debug("REST request to assign an activity from Backlog.");

        Activities assigned = null;
        if (activity.getId() != null) {
            assigned = activitiesService.save(activity);

            taskService.createStorySubtasks(assigned);
        } else {
            return new ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return new ResponseEntity(assigned, HttpStatus.OK);
    }



}
