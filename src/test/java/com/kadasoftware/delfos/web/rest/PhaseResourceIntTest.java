package com.kadasoftware.delfos.web.rest;

import com.kadasoftware.delfos.DelfosApp;
import com.kadasoftware.delfos.domain.Phase;
import com.kadasoftware.delfos.repository.PhaseRepository;
import com.kadasoftware.delfos.service.PhaseService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PhaseResource REST controller.
 *
 * @see PhaseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DelfosApp.class)
public class PhaseResourceIntTest {
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_PARENT = "AAAAA";
    private static final String UPDATED_PARENT = "BBBBB";

    @Inject
    private PhaseRepository phaseRepository;

    @Inject
    private PhaseService phaseService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPhaseMockMvc;

    private Phase phase;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PhaseResource phaseResource = new PhaseResource();
        ReflectionTestUtils.setField(phaseResource, "phaseService", phaseService);
        this.restPhaseMockMvc = MockMvcBuilders.standaloneSetup(phaseResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Phase createEntity() {
        Phase phase = new Phase();
        phase = new Phase()
                .name(DEFAULT_NAME)
                .parent(DEFAULT_PARENT);
        return phase;
    }

    @Before
    public void initTest() {
        phaseRepository.deleteAll();
        phase = createEntity();
    }

    @Test
    public void createPhase() throws Exception {
        int databaseSizeBeforeCreate = phaseRepository.findAll().size();

        // Create the Phase

        restPhaseMockMvc.perform(post("/api/phases")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(phase)))
                .andExpect(status().isCreated());

        // Validate the Phase in the database
        List<Phase> phases = phaseRepository.findAll();
        assertThat(phases).hasSize(databaseSizeBeforeCreate + 1);
        Phase testPhase = phases.get(phases.size() - 1);
        assertThat(testPhase.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPhase.getParent()).isEqualTo(DEFAULT_PARENT);
    }

    @Test
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = phaseRepository.findAll().size();
        // set the field null
        phase.setName(null);

        // Create the Phase, which fails.

        restPhaseMockMvc.perform(post("/api/phases")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(phase)))
                .andExpect(status().isBadRequest());

        List<Phase> phases = phaseRepository.findAll();
        assertThat(phases).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllPhases() throws Exception {
        // Initialize the database
        phaseRepository.save(phase);

        // Get all the phases
        restPhaseMockMvc.perform(get("/api/phases?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(phase.getId())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].parent").value(hasItem(DEFAULT_PARENT.toString())));
    }

    @Test
    public void getPhase() throws Exception {
        // Initialize the database
        phaseRepository.save(phase);

        // Get the phase
        restPhaseMockMvc.perform(get("/api/phases/{id}", phase.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(phase.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.parent").value(DEFAULT_PARENT.toString()));
    }

    @Test
    public void getNonExistingPhase() throws Exception {
        // Get the phase
        restPhaseMockMvc.perform(get("/api/phases/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updatePhase() throws Exception {
        // Initialize the database
        phaseService.save(phase);

        int databaseSizeBeforeUpdate = phaseRepository.findAll().size();

        // Update the phase
        Phase updatedPhase = phaseRepository.findOne(phase.getId());
        updatedPhase
                .name(UPDATED_NAME)
                .parent(UPDATED_PARENT);

        restPhaseMockMvc.perform(put("/api/phases")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedPhase)))
                .andExpect(status().isOk());

        // Validate the Phase in the database
        List<Phase> phases = phaseRepository.findAll();
        assertThat(phases).hasSize(databaseSizeBeforeUpdate);
        Phase testPhase = phases.get(phases.size() - 1);
        assertThat(testPhase.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPhase.getParent()).isEqualTo(UPDATED_PARENT);
    }

    @Test
    public void deletePhase() throws Exception {
        // Initialize the database
        phaseService.save(phase);

        int databaseSizeBeforeDelete = phaseRepository.findAll().size();

        // Get the phase
        restPhaseMockMvc.perform(delete("/api/phases/{id}", phase.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Phase> phases = phaseRepository.findAll();
        assertThat(phases).hasSize(databaseSizeBeforeDelete - 1);
    }
}
