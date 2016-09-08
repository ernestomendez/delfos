package com.kadasoftware.delfos.web.rest;

import com.kadasoftware.delfos.DelfosApp;
import com.kadasoftware.delfos.domain.Activities;
import com.kadasoftware.delfos.repository.ActivitiesRepository;
import com.kadasoftware.delfos.service.ActivitiesService;

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

import com.kadasoftware.delfos.domain.enumeration.ActivityStatus;
import com.kadasoftware.delfos.domain.enumeration.ActivityType;
/**
 * Test class for the ActivitiesResource REST controller.
 *
 * @see ActivitiesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DelfosApp.class)
public class ActivitiesResourceIntTest {
    private static final String DEFAULT_NAME = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";
    private static final String DEFAULT_ACCEPTANCE_CRITERIA = "AAAAA";
    private static final String UPDATED_ACCEPTANCE_CRITERIA = "BBBBB";
    private static final String DEFAULT_ASSIGNED_TO = "AAAAA";
    private static final String UPDATED_ASSIGNED_TO = "BBBBB";
    private static final String DEFAULT_PROJECT = "AAAAA";
    private static final String UPDATED_PROJECT = "BBBBB";

    private static final Integer DEFAULT_STORY_POINTS = 1;
    private static final Integer UPDATED_STORY_POINTS = 2;
    private static final String DEFAULT_SPRINT_WEEK = "AAAAA";
    private static final String UPDATED_SPRINT_WEEK = "BBBBB";

    private static final LocalDate DEFAULT_CREATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_ESTIMATED_TIME = 1;
    private static final Integer UPDATED_ESTIMATED_TIME = 2;

    private static final Float DEFAULT_WOKING_TIME = 1F;
    private static final Float UPDATED_WOKING_TIME = 2F;

    private static final Float DEFAULT_REMAINING_TIME = 1F;
    private static final Float UPDATED_REMAINING_TIME = 2F;

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_CALCULATED_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CALCULATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final ActivityStatus DEFAULT_STATUS = ActivityStatus.Proposed;
    private static final ActivityStatus UPDATED_STATUS = ActivityStatus.Accepted;

    private static final ActivityType DEFAULT_TYPE = ActivityType.Activity;
    private static final ActivityType UPDATED_TYPE = ActivityType.Story;
    private static final String DEFAULT_NOTES = "AAAAA";
    private static final String UPDATED_NOTES = "BBBBB";

    @Inject
    private ActivitiesRepository activitiesRepository;

    @Inject
    private ActivitiesService activitiesService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restActivitiesMockMvc;

    private Activities activities;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ActivitiesResource activitiesResource = new ActivitiesResource();
        ReflectionTestUtils.setField(activitiesResource, "activitiesService", activitiesService);
        this.restActivitiesMockMvc = MockMvcBuilders.standaloneSetup(activitiesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Activities createEntity() {
        Activities activities = new Activities();
        activities = new Activities()
                .name(DEFAULT_NAME)
                .description(DEFAULT_DESCRIPTION)
                .acceptanceCriteria(DEFAULT_ACCEPTANCE_CRITERIA)
                .assignedTo(DEFAULT_ASSIGNED_TO)
                .project(DEFAULT_PROJECT)
                .storyPoints(DEFAULT_STORY_POINTS)
                .sprint_week(DEFAULT_SPRINT_WEEK)
                .creationDate(DEFAULT_CREATION_DATE)
                .estimatedTime(DEFAULT_ESTIMATED_TIME)
                .wokingTime(DEFAULT_WOKING_TIME)
                .remainingTime(DEFAULT_REMAINING_TIME)
                .startDate(DEFAULT_START_DATE)
                .endDate(DEFAULT_END_DATE)
                .calculatedEndDate(DEFAULT_CALCULATED_END_DATE)
                .status(DEFAULT_STATUS)
                .type(DEFAULT_TYPE)
                .notes(DEFAULT_NOTES);
        return activities;
    }

    @Before
    public void initTest() {
        activitiesRepository.deleteAll();
        activities = createEntity();
    }

    @Test
    public void createActivities() throws Exception {
        int databaseSizeBeforeCreate = activitiesRepository.findAll().size();

        // Create the Activities

        restActivitiesMockMvc.perform(post("/api/activities")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(activities)))
                .andExpect(status().isCreated());

        // Validate the Activities in the database
        List<Activities> activities = activitiesRepository.findAll();
        assertThat(activities).hasSize(databaseSizeBeforeCreate + 1);
        Activities testActivities = activities.get(activities.size() - 1);
        assertThat(testActivities.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testActivities.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testActivities.getAcceptanceCriteria()).isEqualTo(DEFAULT_ACCEPTANCE_CRITERIA);
        assertThat(testActivities.getAssignedTo()).isEqualTo(DEFAULT_ASSIGNED_TO);
        assertThat(testActivities.getProject()).isEqualTo(DEFAULT_PROJECT);
        assertThat(testActivities.getStoryPoints()).isEqualTo(DEFAULT_STORY_POINTS);
        assertThat(testActivities.getSprint_week()).isEqualTo(DEFAULT_SPRINT_WEEK);
        assertThat(testActivities.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testActivities.getEstimatedTime()).isEqualTo(DEFAULT_ESTIMATED_TIME);
        assertThat(testActivities.getWokingTime()).isEqualTo(DEFAULT_WOKING_TIME);
        assertThat(testActivities.getRemainingTime()).isEqualTo(DEFAULT_REMAINING_TIME);
        assertThat(testActivities.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testActivities.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testActivities.getCalculatedEndDate()).isEqualTo(DEFAULT_CALCULATED_END_DATE);
        assertThat(testActivities.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testActivities.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testActivities.getNotes()).isEqualTo(DEFAULT_NOTES);
    }

    @Test
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = activitiesRepository.findAll().size();
        // set the field null
        activities.setName(null);

        // Create the Activities, which fails.

        restActivitiesMockMvc.perform(post("/api/activities")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(activities)))
                .andExpect(status().isBadRequest());

        List<Activities> activities = activitiesRepository.findAll();
        assertThat(activities).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkProjectIsRequired() throws Exception {
        int databaseSizeBeforeTest = activitiesRepository.findAll().size();
        // set the field null
        activities.setProject(null);

        // Create the Activities, which fails.

        restActivitiesMockMvc.perform(post("/api/activities")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(activities)))
                .andExpect(status().isBadRequest());

        List<Activities> activities = activitiesRepository.findAll();
        assertThat(activities).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkSprint_weekIsRequired() throws Exception {
        int databaseSizeBeforeTest = activitiesRepository.findAll().size();
        // set the field null
        activities.setSprint_week(null);

        // Create the Activities, which fails.

        restActivitiesMockMvc.perform(post("/api/activities")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(activities)))
                .andExpect(status().isBadRequest());

        List<Activities> activities = activitiesRepository.findAll();
        assertThat(activities).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkCreationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = activitiesRepository.findAll().size();
        // set the field null
        activities.setCreationDate(null);

        // Create the Activities, which fails.

        restActivitiesMockMvc.perform(post("/api/activities")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(activities)))
                .andExpect(status().isBadRequest());

        List<Activities> activities = activitiesRepository.findAll();
        assertThat(activities).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkEstimatedTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = activitiesRepository.findAll().size();
        // set the field null
        activities.setEstimatedTime(null);

        // Create the Activities, which fails.

        restActivitiesMockMvc.perform(post("/api/activities")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(activities)))
                .andExpect(status().isBadRequest());

        List<Activities> activities = activitiesRepository.findAll();
        assertThat(activities).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = activitiesRepository.findAll().size();
        // set the field null
        activities.setStatus(null);

        // Create the Activities, which fails.

        restActivitiesMockMvc.perform(post("/api/activities")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(activities)))
                .andExpect(status().isBadRequest());

        List<Activities> activities = activitiesRepository.findAll();
        assertThat(activities).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = activitiesRepository.findAll().size();
        // set the field null
        activities.setType(null);

        // Create the Activities, which fails.

        restActivitiesMockMvc.perform(post("/api/activities")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(activities)))
                .andExpect(status().isBadRequest());

        List<Activities> activities = activitiesRepository.findAll();
        assertThat(activities).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllActivities() throws Exception {
        // Initialize the database
        activitiesRepository.save(activities);

        // Get all the activities
        restActivitiesMockMvc.perform(get("/api/activities?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(activities.getId())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].acceptanceCriteria").value(hasItem(DEFAULT_ACCEPTANCE_CRITERIA.toString())))
                .andExpect(jsonPath("$.[*].assignedTo").value(hasItem(DEFAULT_ASSIGNED_TO.toString())))
                .andExpect(jsonPath("$.[*].project").value(hasItem(DEFAULT_PROJECT.toString())))
                .andExpect(jsonPath("$.[*].storyPoints").value(hasItem(DEFAULT_STORY_POINTS)))
                .andExpect(jsonPath("$.[*].sprint_week").value(hasItem(DEFAULT_SPRINT_WEEK.toString())))
                .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
                .andExpect(jsonPath("$.[*].estimatedTime").value(hasItem(DEFAULT_ESTIMATED_TIME)))
                .andExpect(jsonPath("$.[*].wokingTime").value(hasItem(DEFAULT_WOKING_TIME.doubleValue())))
                .andExpect(jsonPath("$.[*].remainingTime").value(hasItem(DEFAULT_REMAINING_TIME.doubleValue())))
                .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
                .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
                .andExpect(jsonPath("$.[*].calculatedEndDate").value(hasItem(DEFAULT_CALCULATED_END_DATE.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
                .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
                .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES.toString())));
    }

    @Test
    public void getActivities() throws Exception {
        // Initialize the database
        activitiesRepository.save(activities);

        // Get the activities
        restActivitiesMockMvc.perform(get("/api/activities/{id}", activities.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(activities.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.acceptanceCriteria").value(DEFAULT_ACCEPTANCE_CRITERIA.toString()))
            .andExpect(jsonPath("$.assignedTo").value(DEFAULT_ASSIGNED_TO.toString()))
            .andExpect(jsonPath("$.project").value(DEFAULT_PROJECT.toString()))
            .andExpect(jsonPath("$.storyPoints").value(DEFAULT_STORY_POINTS))
            .andExpect(jsonPath("$.sprint_week").value(DEFAULT_SPRINT_WEEK.toString()))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.estimatedTime").value(DEFAULT_ESTIMATED_TIME))
            .andExpect(jsonPath("$.wokingTime").value(DEFAULT_WOKING_TIME.doubleValue()))
            .andExpect(jsonPath("$.remainingTime").value(DEFAULT_REMAINING_TIME.doubleValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.calculatedEndDate").value(DEFAULT_CALCULATED_END_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES.toString()));
    }

    @Test
    public void getNonExistingActivities() throws Exception {
        // Get the activities
        restActivitiesMockMvc.perform(get("/api/activities/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateActivities() throws Exception {
        // Initialize the database
        activitiesService.save(activities);

        int databaseSizeBeforeUpdate = activitiesRepository.findAll().size();

        // Update the activities
        Activities updatedActivities = activitiesRepository.findOne(activities.getId());
        updatedActivities
                .name(UPDATED_NAME)
                .description(UPDATED_DESCRIPTION)
                .acceptanceCriteria(UPDATED_ACCEPTANCE_CRITERIA)
                .assignedTo(UPDATED_ASSIGNED_TO)
                .project(UPDATED_PROJECT)
                .storyPoints(UPDATED_STORY_POINTS)
                .sprint_week(UPDATED_SPRINT_WEEK)
                .creationDate(UPDATED_CREATION_DATE)
                .estimatedTime(UPDATED_ESTIMATED_TIME)
                .wokingTime(UPDATED_WOKING_TIME)
                .remainingTime(UPDATED_REMAINING_TIME)
                .startDate(UPDATED_START_DATE)
                .endDate(UPDATED_END_DATE)
                .calculatedEndDate(UPDATED_CALCULATED_END_DATE)
                .status(UPDATED_STATUS)
                .type(UPDATED_TYPE)
                .notes(UPDATED_NOTES);

        restActivitiesMockMvc.perform(put("/api/activities")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedActivities)))
                .andExpect(status().isOk());

        // Validate the Activities in the database
        List<Activities> activities = activitiesRepository.findAll();
        assertThat(activities).hasSize(databaseSizeBeforeUpdate);
        Activities testActivities = activities.get(activities.size() - 1);
        assertThat(testActivities.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testActivities.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testActivities.getAcceptanceCriteria()).isEqualTo(UPDATED_ACCEPTANCE_CRITERIA);
        assertThat(testActivities.getAssignedTo()).isEqualTo(UPDATED_ASSIGNED_TO);
        assertThat(testActivities.getProject()).isEqualTo(UPDATED_PROJECT);
        assertThat(testActivities.getStoryPoints()).isEqualTo(UPDATED_STORY_POINTS);
        assertThat(testActivities.getSprint_week()).isEqualTo(UPDATED_SPRINT_WEEK);
        assertThat(testActivities.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testActivities.getEstimatedTime()).isEqualTo(UPDATED_ESTIMATED_TIME);
        assertThat(testActivities.getWokingTime()).isEqualTo(UPDATED_WOKING_TIME);
        assertThat(testActivities.getRemainingTime()).isEqualTo(UPDATED_REMAINING_TIME);
        assertThat(testActivities.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testActivities.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testActivities.getCalculatedEndDate()).isEqualTo(UPDATED_CALCULATED_END_DATE);
        assertThat(testActivities.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testActivities.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testActivities.getNotes()).isEqualTo(UPDATED_NOTES);
    }

    @Test
    public void deleteActivities() throws Exception {
        // Initialize the database
        activitiesService.save(activities);

        int databaseSizeBeforeDelete = activitiesRepository.findAll().size();

        // Get the activities
        restActivitiesMockMvc.perform(delete("/api/activities/{id}", activities.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Activities> activities = activitiesRepository.findAll();
        assertThat(activities).hasSize(databaseSizeBeforeDelete - 1);
    }
}
