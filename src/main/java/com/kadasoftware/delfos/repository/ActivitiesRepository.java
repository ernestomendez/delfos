package com.kadasoftware.delfos.repository;

import com.kadasoftware.delfos.domain.Activities;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Spring Data MongoDB repository for the Activities entity.
 */
@SuppressWarnings("unused")
public interface ActivitiesRepository extends MongoRepository<Activities,String> {

    /**
     * Gets all the activities by a project and a sprint given.
     *
     * @param project project name to find.
     * @param sprintId sprint Id to find.
     * @return a list of activities given a project and a sprint.
     */
    List<Activities> findAllByProjectAndSprintWeek(String project, String sprintId);
}
