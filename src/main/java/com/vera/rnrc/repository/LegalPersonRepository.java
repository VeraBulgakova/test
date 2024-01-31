package com.vera.rnrc.repository;

import com.vera.rnrc.entity.LegalPersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LegalPersonRepository extends JpaRepository<LegalPersonEntity, Long> {
    @Query(value = "SELECT * " +
            "FROM legal_person lp " +
            "JOIN rnrc_ref_partner rnp ON lp.id = rnp.id where lp.inn=rnp.inn;", nativeQuery = true)
    List<LegalPersonEntity> findMatchingRecords();

    @Query("SELECT v FROM LegalPersonEntity v WHERE v.dateList = ?1 AND v.listName = ?2")
    List<LegalPersonEntity> findByCustomCondition(String dateList, String listName);
}