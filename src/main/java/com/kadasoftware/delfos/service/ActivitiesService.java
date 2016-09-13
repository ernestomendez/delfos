package com.kadasoftware.delfos.service;

import com.kadasoftware.delfos.domain.Activities;
import com.kadasoftware.delfos.repository.ActivitiesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Activities.
 */
@Service
public class ActivitiesService {

    private final Logger log = LoggerFactory.getLogger(ActivitiesService.class);
    
    @Inject
    private ActivitiesRepository activitiesRepository;

    /**
     * Save a activities.
     *
     * @param activities the entity to save
     * @return the persisted entity
     */
    public Activities save(Activities activities) {
        log.debug("Request to save Activities : {}", activities);
        Activities result = activitiesRepository.save(activities);
        return result;
    }

    /**
     *  Get all the activities.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    public Page<Activities> findAll(Pageable pageable) {
        log.debug("Request to get all Activities");
        Page<Activities> result = activitiesRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one activities by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    public Activities findOne(String id) {
        log.debug("Request to get Activities : {}", id);
        Activities activities = activitiesRepository.findOne(id);
        return activities;
    }

    /**
     *  Delete the  activities by id.
     *
     *  @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Activities : {}", id);
        activitiesRepository.delete(id);
    }
}
