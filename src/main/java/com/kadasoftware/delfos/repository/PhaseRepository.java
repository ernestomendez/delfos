package com.kadasoftware.delfos.repository;

import com.kadasoftware.delfos.domain.Phase;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Phase entity.
 */
@SuppressWarnings("unused")
public interface PhaseRepository extends MongoRepository<Phase,String> {

}
