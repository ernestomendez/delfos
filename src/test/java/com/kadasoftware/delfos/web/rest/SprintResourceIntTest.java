package com.kadasoftware.delfos.web.rest;

import com.kadasoftware.delfos.DelfosApp;
import com.kadasoftware.delfos.domain.Sprint;
import com.kadasoftware.delfos.repository.SprintRepository;
import com.kadasoftware.delfos.service.SprintService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SprintResource REST controller.
 *
 * @see SprintResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DelfosApp.class)
public class SprintResourceIntTest {
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_PROJECT = "AAAAA";
    private static final String UPDATED_PROJECT = "BBBBB";

    @Inject
    private SprintRepository sprintRepository;

    @Inject
    private SprintService sprintService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restSprintMockMvc;

    private Sprint sprint;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SprintResource sprintResource = new SprintResource();
        ReflectionTestUtils.setField(sprintResource, "sprintService", sprintService);
        this.restSprintMockMvc = MockMvcBuilders.standaloneSetup(sprintResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sprint createEntity() {
        Sprint sprint = new Sprint();
        sprint = new Sprint()
                .name(DEFAULT_NAME)
                .startDate(DEFAULT_START_DATE)
                .endDate(DEFAULT_END_DATE)
                .project(DEFAULT_PROJECT);
        return sprint;
    }

    @Before
    public void initTest() {
        sprintRepository.deleteAll();
        sprint = createEntity();
    }

    @Test
    public void createSprint() throws Exception {
        int databaseSizeBeforeCreate = sprintRepository.findAll().size();

        // Create the Sprint

        restSprintMockMvc.perform(post("/api/sprints")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(sprint)))
                .andExpect(status().isCreated());

        // Validate the Sprint in the database
        List<Sprint> sprints = sprintRepository.findAll();
        assertThat(sprints).hasSize(databaseSizeBeforeCreate + 1);
        Sprint testSprint = sprints.get(sprints.size() - 1);
        assertThat(testSprint.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSprint.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testSprint.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testSprint.getProject()).isEqualTo(DEFAULT_PROJECT);
    }

    @Test
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = sprintRepository.findAll().size();
        // set the field null
        sprint.setName(null);

        // Create the Sprint, which fails.

        restSprintMockMvc.perform(post("/api/sprints")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(sprint)))
                .andExpect(status().isBadRequest());

        List<Sprint> sprints = sprintRepository.findAll();
        assertThat(sprints).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = sprintRepository.findAll().size();
        // set the field null
        sprint.setStartDate(null);

        // Create the Sprint, which fails.

        restSprintMockMvc.perform(post("/api/sprints")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(sprint)))
                .andExpect(status().isBadRequest());

        List<Sprint> sprints = sprintRepository.findAll();
        assertThat(sprints).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkEndDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = sprintRepository.findAll().size();
        // set the field null
        sprint.setEndDate(null);

        // Create the Sprint, which fails.

        restSprintMockMvc.perform(post("/api/sprints")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(sprint)))
                .andExpect(status().isBadRequest());

        List<Sprint> sprints = sprintRepository.findAll();
        assertThat(sprints).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkProjectIsRequired() throws Exception {
        int databaseSizeBeforeTest = sprintRepository.findAll().size();
        // set the field null
        sprint.setProject(null);

        // Create the Sprint, which fails.

        restSprintMockMvc.perform(post("/api/sprints")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(sprint)))
                .andExpect(status().isBadRequest());

        List<Sprint> sprints = sprintRepository.findAll();
        assertThat(sprints).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllSprints() throws Exception {
        // Initialize the database
        sprintRepository.save(sprint);

        // Get all the sprints
        restSprintMockMvc.perform(get("/api/sprints?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(sprint.getId())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
                .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
                .andExpect(jsonPath("$.[*].project").value(hasItem(DEFAULT_PROJECT.toString())));
    }

    @Test
    public void getSprint() throws Exception {
        // Initialize the database
        sprintRepository.save(sprint);

        // Get the sprint
        restSprintMockMvc.perform(get("/api/sprints/{id}", sprint.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sprint.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.project").value(DEFAULT_PROJECT.toString()));
    }

    @Test
    public void getNonExistingSprint() throws Exception {
        // Get the sprint
        restSprintMockMvc.perform(get("/api/sprints/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateSprint() throws Exception {
        // Initialize the database
        sprintService.save(sprint);

        int databaseSizeBeforeUpdate = sprintRepository.findAll().size();

        // Update the sprint
        Sprint updatedSprint = sprintRepository.findOne(sprint.getId());
        updatedSprint
                .name(UPDATED_NAME)
                .startDate(UPDATED_START_DATE)
                .endDate(UPDATED_END_DATE)
                .project(UPDATED_PROJECT);

        restSprintMockMvc.perform(put("/api/sprints")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedSprint)))
                .andExpect(status().isOk());

        // Validate the Sprint in the database
        List<Sprint> sprints = sprintRepository.findAll();
        assertThat(sprints).hasSize(databaseSizeBeforeUpdate);
        Sprint testSprint = sprints.get(sprints.size() - 1);
        assertThat(testSprint.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSprint.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testSprint.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testSprint.getProject()).isEqualTo(UPDATED_PROJECT);
    }

    @Test
    public void deleteSprint() throws Exception {
        // Initialize the database
        sprintService.save(sprint);

        int databaseSizeBeforeDelete = sprintRepository.findAll().size();

        // Get the sprint
        restSprintMockMvc.perform(delete("/api/sprints/{id}", sprint.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Sprint> sprints = sprintRepository.findAll();
        assertThat(sprints).hasSize(databaseSizeBeforeDelete - 1);
    }
}
