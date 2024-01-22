package com.vera.rnrc.repository;

import com.vera.rnrc.entity.PhysicalPersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhysicalPersonRepository extends JpaRepository<PhysicalPersonEntity, Long> {
    @Query(value = "SELECT * " +
            "FROM physical_person pp " +
            "JOIN index_olr_partnerlinkedstructure pls ON pp.inn = pls.inn " +
            "AND pp.passportSeries = pls.docseries " +
            "AND pp.passportNumber = pls.docnumber " +
//            "AND pp.full_name = pls.fullname "+
            "AND pp.surname = pls.lastname " +
            "AND pp.name = pls.firstname " +
            "AND pp.patronimic = pls.middlename " +
            "AND pp.placeOfBirth = pls.placeofbirth " +
            "AND pp.dateOfBirth = pls.dateofbirth;", nativeQuery = true)
    List<PhysicalPersonEntity> findMatchingRecords(String someCondition);
}