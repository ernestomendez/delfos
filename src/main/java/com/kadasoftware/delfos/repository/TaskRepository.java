package com.kadasoftware.delfos.repository;

import com.kadasoftware.delfos.domain.Task;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Task entity.
 */
@SuppressWarnings("unused")
public interface TaskRepository extends MongoRepository<Task,String> {

}
