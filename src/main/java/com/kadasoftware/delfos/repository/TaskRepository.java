package com.kadasoftware.delfos.repository;

import com.kadasoftware.delfos.domain.Task;

import com.kadasoftware.delfos.domain.enumeration.TaskStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Spring Data MongoDB repository for the Task entity.
 */
@SuppressWarnings("unused")
public interface TaskRepository extends MongoRepository<Task,String> {

    void deleteAllByActivityId(String activityId);

    List<Task> findAllByActivityIdAndStatus(String activityId, TaskStatus taskStatus);
}
