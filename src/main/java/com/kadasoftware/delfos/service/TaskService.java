package com.kadasoftware.delfos.service;

import com.kadasoftware.delfos.domain.Task;
import com.kadasoftware.delfos.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

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
}
