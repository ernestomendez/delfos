package com.kadasoftware.delfos.service;

import com.kadasoftware.delfos.domain.Phase;
import com.kadasoftware.delfos.repository.PhaseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Phase.
 */
@Service
public class PhaseService {

    private final Logger log = LoggerFactory.getLogger(PhaseService.class);
    
    @Inject
    private PhaseRepository phaseRepository;

    /**
     * Save a phase.
     *
     * @param phase the entity to save
     * @return the persisted entity
     */
    public Phase save(Phase phase) {
        log.debug("Request to save Phase : {}", phase);
        Phase result = phaseRepository.save(phase);
        return result;
    }

    /**
     *  Get all the phases.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    public Page<Phase> findAll(Pageable pageable) {
        log.debug("Request to get all Phases");
        Page<Phase> result = phaseRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one phase by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    public Phase findOne(String id) {
        log.debug("Request to get Phase : {}", id);
        Phase phase = phaseRepository.findOne(id);
        return phase;
    }

    /**
     *  Delete the  phase by id.
     *
     *  @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Phase : {}", id);
        phaseRepository.delete(id);
    }
}
