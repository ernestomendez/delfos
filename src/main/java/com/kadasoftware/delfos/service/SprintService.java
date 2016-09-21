package com.kadasoftware.delfos.service;

import com.kadasoftware.delfos.domain.Sprint;
import com.kadasoftware.delfos.repository.SprintRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.List;

/**
 * Service Implementation for managing Sprint.
 */
@Service
public class SprintService {

    private final Logger log = LoggerFactory.getLogger(SprintService.class);

    @Inject
    private SprintRepository sprintRepository;

    /**
     * Save a sprint.
     *
     * @param sprint the entity to save
     * @return the persisted entity
     */
    public Sprint save(Sprint sprint) {
        log.debug("Request to save Sprint : {}", sprint);
        Sprint result = sprintRepository.save(sprint);
        return result;
    }

    /**
     *  Get all the sprints.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    public Page<Sprint> findAll(Pageable pageable) {
        log.debug("Request to get all Sprints");
        Page<Sprint> result = sprintRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one sprint by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    public Sprint findOne(String id) {
        log.debug("Request to get Sprint : {}", id);
        Sprint sprint = sprintRepository.findOne(id);
        return sprint;
    }

    /**
     *  Delete the  sprint by id.
     *
     *  @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Sprint : {}", id);
        sprintRepository.delete(id);
    }

    /**
     * Finds all the active sprints, today.
     * @return list of active sprints.
     */
    public List<Sprint> findTodayAllActiveSprints(String project, LocalDate day) {
        log.debug("Request to get all active sprints of today");

        return sprintRepository.findByProjectAndStartDateBeforeAndEndDateAfter(project, day, day);
    }
}
