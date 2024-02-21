package com.vera.rnrc.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class ResponseRepositoryImpl {
    private final ResponseRepository responseRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void createViewForPhysicPerson(@Param("dateList") String dateList, @Param("listName") String listName) {
        String sql = responseRepository.createViewForPhysicalPerson(dateList, listName);
        entityManager.createNativeQuery(sql).executeUpdate();
    }


    @Transactional
    public void createViewForLegalPerson(@Param("dateList") String dateList, @Param("listName") String listName) {
        String sql = responseRepository.createViewForLegalPerson(dateList, listName);
        entityManager.createNativeQuery(sql).executeUpdate();
    }
}
