package com.kadasoftware.delfos.repository;

import com.kadasoftware.delfos.domain.Activities;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Activities entity.
 */
@SuppressWarnings("unused")
public interface ActivitiesRepository extends MongoRepository<Activities,String> {

}
