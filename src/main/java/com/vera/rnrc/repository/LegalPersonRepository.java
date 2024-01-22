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
            "JOIN rnrc_ref_partner rnp ON lp.inn = rnp.inn " +
            "AND lp.ogrn = rnp.ogrn " +
            "AND lp.organization_name = rnp.fullname;", nativeQuery = true)
    List<LegalPersonEntity> findMatchingRecords(String someCondition);
}