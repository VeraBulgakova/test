package com.vera.rnrc.repository;

import com.vera.rnrc.entity.ResponseEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public interface ResponseRepository extends JpaRepository<ResponseEntity, Long> {
    @Modifying
    @Transactional
    @Query(value = """
            insert into response(partner_id, check_object, linked_structure_order, participant_order
                , short_name, check_result, list_id)
            select r.id          as partner_id, 
                   'Контрагент'  as check_object, 
                   null          as linked_structure_order, 
                   null          as participant_order, 
                   r.partnername as short_name, 
                   Max(CASE 
                           WHEN l.id is not null 
                               THEN CONCAT('Идентифицирован по перечню от ') 
                           ELSE CONCAT('Не идентифицирован по перечню от ') 
                       END  )     as check_result, 
                   max(l.id)          as list_id 
            FROM rnrc_ref_partner r 
                     left JOIN viewLegal l ON r.id = l.partner_id 
            group by r.id, r.partnername 
            union 
            select iop.id                     as partner_id, 
                   CASE iop.linkedstructuretype 
                       WHEN 'Beneficiary' THEN 'Бенефициар' 
                       WHEN 'BenefitHolder' THEN 'Выгодоприобретатель' 
                       WHEN 'ManagementBody' THEN 'Управляющий орган' 
                       WHEN 'Representative' THEN 'Представитель' 
                       ELSE 'Контрагент' 
                       END                    as check_object, 
                   max(CASE 
                           WHEN iop.linkedstructuretype = 'ManagementBody' THEN iop.managementbodyordernumber 
                           ELSE iop.participantordernumber 
                       END)                   as linked_structure_order, 
                   iop.participantordernumber as participant_order, 
                   max(CASE 
                           WHEN CHAR_LENGTH(TRIM(CONCAT(iop.lastname, ' ', iop.firstname, ' ', iop.middlename))) < 2 
                               THEN SPLIT_PART(fullname, ', ', 2) 
                           ELSE TRIM(CONCAT(iop.lastname, ' ', iop.firstname, ' ', iop.middlename)) 
                       END)                   as short_name, 
                   max(CASE 
                           WHEN v.id is not null 
                               THEN CONCAT('Идентифицирован по перечню от ') 
                           ELSE CONCAT('Не идентифицирован по перечню от ') 
                       END)                   as check_result, 
                   max(v.id)                  as list_id 
            FROM index_olr_partnerlinkedstructure iop 
                     left join viewPhysical v on v.partner_id = iop.pzinskey 
            group by iop.id, iop.participantordernumber, iop.managementbodyordernumber, iop.linkedstructuretype ;
            """, nativeQuery = true)
    void insertResponseRecordsFromTable();

    @Modifying
    @Transactional
    @Query(value = "truncate table response;", nativeQuery = true)
    void cleanResultTable();

    @Modifying
    @Transactional
    @Query(value = "truncate table legal_person;", nativeQuery = true)
    void cleanLegalPersonTable();

    @Modifying
    @Transactional
    @Query(value = "truncate table physical_person;", nativeQuery = true)
    void cleanPhysicPersonTable();


    List<ResponseEntity> findAllByPartnerId(@RequestParam("partnerId") String partnerId);

    default String createViewForPhysicalPerson(String dateList, String listName) {
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

    default String createViewForLegalPerson(String dateList, String listName) {
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
