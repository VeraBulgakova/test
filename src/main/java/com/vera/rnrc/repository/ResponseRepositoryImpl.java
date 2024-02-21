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
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void createViewForPhysicPerson(@Param("dateList") String dateList, @Param("listName") String listName) {
        String sql = createViewForPhysicalPerson(dateList, listName);
        entityManager.createNativeQuery(sql).executeUpdate();
    }


    @Transactional
    public void createViewForLegalPerson(@Param("dateList") String dateList, @Param("listName") String listName) {
        String sql = createViewForLegalEntity(dateList, listName);
        entityManager.createNativeQuery(sql).executeUpdate();
    }

    private String createViewForPhysicalPerson(String dateList, String listName) {
        String sql = "CREATE or replace VIEW viewPhysical AS " +
                "SELECT p.id, iop.pzinskey as partner_id " +
                "FROM index_olr_partnerlinkedstructure iop " +
                "         JOIN physical_person p ON " +
                "    p.inn = iop.inn " +
                "        AND p.listname = '" + listName.replace("'", "''") + "' " +
                "        AND p.datelist = '" + dateList.replace("'", "''") + "' " +
                "union " +
                "SELECT p.id, iop.pzinskey as partner_id " +
                "FROM index_olr_partnerlinkedstructure iop " +
                "         JOIN physical_person p ON " +
                "    p.docseries = iop.docseries AND p.docnumber = iop.docnumber " +
                "        AND p.listname = '" + listName.replace("'", "''") + "' " +
                "        AND p.datelist = '" + dateList.replace("'", "''") + "' " +
                "union " +
                "SELECT p.id, iop.pzinskey as partner_id " +
                "FROM index_olr_partnerlinkedstructure iop " +
                "         JOIN physical_person p ON " +
                "    p.dateofbirth = iop.dateofbirth " +
                "        AND LOWER(p.lastname) = LOWER(iop.lastname) AND LOWER(p.firstname) = LOWER(iop.firstname) " +
                "        AND LOWER(p.middlename) = LOWER(iop.middlename) " +
                "        AND p.listname = '" + listName.replace("'", "''") + "' " +
                "        AND p.datelist = '" + dateList.replace("'", "''") + "' " +
                "union " +
                "SELECT p.id, iop.pzinskey as partner_id " +
                "FROM index_olr_partnerlinkedstructure iop " +
                "         JOIN physical_person p ON " +
                "    p.dateofbirth = iop.dateofbirth " +
                "        AND LOWER(p.fullname) = LOWER(iop.fullname) " +
                "        AND p.listname = '" + listName.replace("'", "''") + "' " +
                "        AND p.datelist = '" + dateList.replace("'", "''") + "' ;";
        return sql;
    }

    private String createViewForLegalEntity(String dateList, String listName) {
        String sql = "CREATE or replace VIEW viewLegal AS " +
                "SELECT l.id, r.id as partner_id " +
                "FROM rnrc_ref_partner r " +
                "         JOIN legal_person l ON " +
                "        l.inn = r.inn " +
                "        AND l.listname = '" + listName.replace("'", "''") + "' " +
                "        AND l.datelist = '" + dateList.replace("'", "''") + "' " +
                "union " +
                "SELECT l.id, r.id as partner_id " +
                "FROM rnrc_ref_partner r " +
                "         JOIN legal_person l ON " +
                "        l.ogrn = r.ogrn " +
                "        AND l.listname = '" + listName.replace("'", "''") + "' " +
                "        AND l.datelist = '" + dateList.replace("'", "''") + "' " +
                "union " +
                "SELECT l.id, r.id as partner_id " +
                "FROM rnrc_ref_partner r " +
                "         JOIN legal_person l ON " +
                "        l.fullname = r.fullname " +
                "        AND l.listname = '" + listName.replace("'", "''") + "' " +
                "        AND l.datelist = '" + dateList.replace("'", "''") + "' ";
        return sql;
    }
}
