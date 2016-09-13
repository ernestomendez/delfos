package com.kadasoftware.delfos.repository;

import com.kadasoftware.delfos.domain.Sprint;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Sprint entity.
 */
@SuppressWarnings("unused")
public interface SprintRepository extends MongoRepository<Sprint,String> {

}
