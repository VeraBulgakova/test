package com.vera.rnrc.repository;

import com.vera.rnrc.entity.PhysicalPersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhysicalPersonRepository extends JpaRepository<PhysicalPersonEntity, Long> {
    //    @Query(value = "select p.id, p.full_name,p.name from physical_person p join index_olr_partnerlinkedstructure iop " +
//            " on p.id = iop.id where p.name=iop.firstname;", nativeQuery = true)
    @Query(value = "select p.id,p.date_list, " +
            "p.list_name, " +
            "p.inn, " +
            "p.passport_series, " +
            "p.passport_number, " +
            "p.full_name, " +
            "p.surname, " +
            "p.name, " +
            "p.patronymic, " +
            "p.date_of_birth, " +
            "p.place_of_birth, " +
            "p.resident_sign " +
            "from physical_person p " +
            "join index_olr_partnerlinkedstructure iop " +
            "on p.id = iop.id " +
            "where p.name = iop.firstname;", nativeQuery = true)
    List<PhysicalPersonEntity> findMatchingRecords();
}