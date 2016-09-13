package com.kadasoftware.delfos.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.kadasoftware.delfos.domain.Phase;
import com.kadasoftware.delfos.service.PhaseService;
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
 * REST controller for managing Phase.
 */
@RestController
@RequestMapping("/api")
public class PhaseResource {

    private final Logger log = LoggerFactory.getLogger(PhaseResource.class);
        
    @Inject
    private PhaseService phaseService;

    /**
     * POST  /phases : Create a new phase.
     *
     * @param phase the phase to create
     * @return the ResponseEntity with status 201 (Created) and with body the new phase, or with status 400 (Bad Request) if the phase has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/phases",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Phase> createPhase(@Valid @RequestBody Phase phase) throws URISyntaxException {
        log.debug("REST request to save Phase : {}", phase);
        if (phase.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("phase", "idexists", "A new phase cannot already have an ID")).body(null);
        }
        Phase result = phaseService.save(phase);
        return ResponseEntity.created(new URI("/api/phases/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("phase", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /phases : Updates an existing phase.
     *
     * @param phase the phase to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated phase,
     * or with status 400 (Bad Request) if the phase is not valid,
     * or with status 500 (Internal Server Error) if the phase couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/phases",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Phase> updatePhase(@Valid @RequestBody Phase phase) throws URISyntaxException {
        log.debug("REST request to update Phase : {}", phase);
        if (phase.getId() == null) {
            return createPhase(phase);
        }
        Phase result = phaseService.save(phase);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("phase", phase.getId().toString()))
            .body(result);
    }

    /**
     * GET  /phases : get all the phases.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of phases in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/phases",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Phase>> getAllPhases(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Phases");
        Page<Phase> page = phaseService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/phases");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /phases/:id : get the "id" phase.
     *
     * @param id the id of the phase to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the phase, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/phases/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Phase> getPhase(@PathVariable String id) {
        log.debug("REST request to get Phase : {}", id);
        Phase phase = phaseService.findOne(id);
        return Optional.ofNullable(phase)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /phases/:id : delete the "id" phase.
     *
     * @param id the id of the phase to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/phases/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePhase(@PathVariable String id) {
        log.debug("REST request to delete Phase : {}", id);
        phaseService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("phase", id.toString())).build();
    }

}
