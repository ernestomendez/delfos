package com.kadasoftware.delfos.repository;

import com.kadasoftware.delfos.domain.Projects;

import com.kadasoftware.delfos.service.dto.UserForProject;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

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
     * @param login login for the user assigned to the project.
     * @return list of projects.
     */
    @Query("{'users': {$elemMatch: {'login': ?0}}}")
    List<Projects> findByUsersContaining(String login);

    /**
     * Finds a project by it's name.
     *
     * @param name project name.
     * @return a project with the given name, null other wise.
     */
    Projects findOneByName(String name);
}
