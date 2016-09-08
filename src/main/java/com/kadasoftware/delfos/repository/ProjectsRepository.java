package com.kadasoftware.delfos.repository;

import com.kadasoftware.delfos.domain.Projects;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Projects entity.
 */
@SuppressWarnings("unused")
public interface ProjectsRepository extends MongoRepository<Projects,String> {

}
