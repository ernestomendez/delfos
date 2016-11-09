package com.kadasoftware.delfos.service;

import com.kadasoftware.delfos.domain.Activities;
import com.kadasoftware.delfos.domain.Task;
import com.kadasoftware.delfos.domain.enumeration.ActivityStatus;
import com.kadasoftware.delfos.domain.enumeration.SoftwareProcess;
import com.kadasoftware.delfos.domain.enumeration.TaskStatus;
import com.kadasoftware.delfos.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Stream;

/**
 * Service Implementation for managing Task.
 */
@Service
public class TaskService {

    private final Logger log = LoggerFactory.getLogger(TaskService.class);

    @Inject
    private TaskRepository taskRepository;

    /**
     * Save a task.
     *
     * @param task the entity to save
     * @return the persisted entity
     */
    public Task save(Task task) {
        log.debug("Request to save Task : {}", task);
        Task result = taskRepository.save(task);
        return result;
    }

    /**
     *  Get all the tasks.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    public Page<Task> findAll(Pageable pageable) {
        log.debug("Request to get all Tasks");
        Page<Task> result = taskRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one task by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    public Task findOne(String id) {
        log.debug("Request to get Task : {}", id);
        Task task = taskRepository.findOne(id);
        return task;
    }

    /**
     *  Delete the  task by id.
     *
     *  @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Task : {}", id);
        taskRepository.delete(id);
    }

    /**
     * Creates a group of sub tasks for a story, given the software process list.
     * For the moment is the default software process.
     *
     * @param story story to add the tasks.
     */
    public void createStorySubtasks(Activities story) {
        log.debug("Creates a group of sub tasks for a story, given the software process list");
        Assert.notNull(story, "Story can not be null");

        //TODO change the software process for an object list.

        Stream.of(SoftwareProcess.values()).forEach(taskName -> {
            Task task = new Task()
                .name(taskName.getName())
                .phase(taskName.getName())
                .activity(story.getName(), story.getId())
                .creationDate(LocalDate.now())
                .status(TaskStatus.New.name());

            this.save(task);
        });
    }

    /**
     * Removes all sub tasks associated with the given story.
     *
     * @param story story to remove it's sub tasks.
     */
    public void deleteStorySubtasks(Activities story) {
        log.debug("Removes all sub tasks associated with the given story");
        Assert.notNull(story, "Story can not be null");

        taskRepository.deleteAllByActivityId(story.getId());
    }

    public List<Task> findAllByActivityIdAndStatus(String activityId, TaskStatus taskStatus) {
        return taskRepository.findAllByActivityIdAndStatus(activityId, taskStatus);
    }
}
