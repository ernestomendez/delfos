package com.kadasoftware.delfos.repository;

import com.kadasoftware.delfos.domain.Projects;

import com.kadasoftware.delfos.service.dto.UserForProject;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Set;

/**
 * Spring Data MongoDB repository for the Projects entity.
 */
@SuppressWarnings("unused")
public interface ProjectsRepository extends MongoRepository<Projects,String> {

    /**
     * Finds all projects where a given user is part of.
     *
     * @param users user assigned to the project.
     * @return list of projects.
     */
    List<Projects> findAllByUsersContaining(UserForProject users);

}
