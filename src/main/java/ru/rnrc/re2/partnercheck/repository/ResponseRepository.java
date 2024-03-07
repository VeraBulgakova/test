package ru.rnrc.re2.partnercheck.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;
import ru.rnrc.re2.partnercheck.entity.Response;

import java.util.List;

@Repository
@Transactional
public interface ResponseRepository extends JpaRepository<Response, Long> {
    @Modifying
    @Query(value = """
                WITH viewLegal AS (
                SELECT l.id, r.partner_id as partner_id
                FROM rnrc_ref_partner r
                         JOIN legal_person l ON l.inn = r.inn
                    AND l.listname = ?1
                    AND l.datelist = ?2
                UNION
                SELECT l.id, r.partner_id as partner_id
                FROM rnrc_ref_partner r
                         JOIN legal_person l ON l.ogrn = r.ogrn
                    AND l.listname = ?1
                    AND l.datelist = ?2
                UNION
                SELECT l.id, r.partner_id as partner_id
                FROM rnrc_ref_partner r
                         JOIN legal_person l ON l.fullname = r.fullname
                    AND l.listname = ?1
                    AND l.datelist = ?2
            )
            , viewPhysical AS (
            SELECT p.id,
                   iop.pzinskey as partner_id
            FROM index_olr_partnerlinkedstructure iop
                     JOIN physical_person p ON
                p.inn = iop.inn
                    AND p.listname = ?1
                    AND p.datelist = ?2
            union
            SELECT p.id, iop.pzinskey as partner_id
            FROM index_olr_partnerlinkedstructure iop
                     JOIN physical_person p ON
                p.docseries = iop.docseries AND p.docnumber = iop.docnumber
                    AND p.listname = ?1
                    AND p.datelist = ?2
            union
            SELECT p.id, iop.pzinskey as partner_id
            FROM index_olr_partnerlinkedstructure iop
                     JOIN physical_person p ON
                p.dateofbirth = iop.dateofbirth
                    and LOWER(p.placeofbirth) = LOWER(iop.placeofbirth)
                    AND LOWER(p.lastname) = LOWER(iop.lastname) AND LOWER(p.firstname) = LOWER(iop.firstname)
                    AND LOWER(p.middlename) = LOWER(iop.middlename)
                    AND p.listname = ?1
                    AND p.datelist = ?2
            union
            SELECT p.id, iop.pzinskey as partner_id
            FROM index_olr_partnerlinkedstructure iop
                     JOIN physical_person p ON
                p.dateofbirth = iop.dateofbirth
                    and LOWER(p.placeofbirth) = LOWER(iop.placeofbirth)
                    AND LOWER(p.fullname) = LOWER(iop.fullname)
                    AND p.listname = ?1
                    AND p.datelist = ?2)
                    
                        insert into response(partner_id, check_object, linked_structure_order, participant_order
                            , short_name, check_result, list_id)
                        -- это для юр лиц
                        select r.partner_id          as partner_id,
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
                                 left JOIN viewLegal l ON r.partner_id = l.partner_id
                        group by r.partner_id, r.partnername
                        union
                        -- это для физ лиц
                        select SUBSTRING(pzinskey FROM '(\\d+)')                     as partner_id,
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
                        group by SUBSTRING(pzinskey FROM '(\\d+)'), iop.participantordernumber, iop.managementbodyordernumber, iop.linkedstructuretype ;
                        """, nativeQuery = true)
    void insertResponseRecordsFromTable(String listName, String dateList);

    @Modifying
    @Query(value = "truncate table response;", nativeQuery = true)
    void cleanResultTable();

    @Modifying
    @Query(value = "truncate table legal_person;", nativeQuery = true)
    void cleanLegalPersonTable();

    @Modifying
    @Query(value = "truncate table physical_person;", nativeQuery = true)
    void cleanPhysicPersonTable();

    List<Response> findAllByPartnerId(@RequestParam("partnerId") String partnerId);

    @Query(value = """ 
              SELECT COUNT(*) FROM (
              SELECT l.id, r.partner_id as partner_id
              FROM rnrc_ref_partner r
                       JOIN legal_person l ON l.inn = r.inn
                  AND l.listname = ?1
                  AND l.datelist = ?2
              UNION
              SELECT l.id, r.partner_id as partner_id
              FROM rnrc_ref_partner r
                       JOIN legal_person l ON l.ogrn = r.ogrn
                  AND l.listname = ?1
                  AND l.datelist = ?2
              UNION
              SELECT l.id, r.partner_id as partner_id
              FROM rnrc_ref_partner r
                       JOIN legal_person l ON l.fullname = r.fullname
                  AND l.listname = ?1
                  AND l.datelist = ?2
            union
              SELECT p.id,
                     iop.pzinskey as partner_id
              FROM index_olr_partnerlinkedstructure iop
                       JOIN physical_person p ON
                  p.inn = iop.inn
                      AND p.listname = ?1
                      AND p.datelist = ?2
              union
              SELECT p.id, iop.pzinskey as partner_id
              FROM index_olr_partnerlinkedstructure iop
                       JOIN physical_person p ON
                  p.docseries = iop.docseries AND p.docnumber = iop.docnumber
                      AND p.listname = ?1
                      AND p.datelist = ?2
              union
              SELECT p.id, iop.pzinskey as partner_id
              FROM index_olr_partnerlinkedstructure iop
                       JOIN physical_person p ON
                  p.dateofbirth = iop.dateofbirth
                      and LOWER(p.placeofbirth) = LOWER(iop.placeofbirth)
                      AND LOWER(p.lastname) = LOWER(iop.lastname) AND LOWER(p.firstname) = LOWER(iop.firstname)
                      AND LOWER(p.middlename) = LOWER(iop.middlename)
                      AND p.listname = ?1
                      AND p.datelist = ?2
              union
              SELECT p.id, iop.pzinskey as partner_id
              FROM index_olr_partnerlinkedstructure iop
                       JOIN physical_person p ON
                  p.dateofbirth = iop.dateofbirth
                      and LOWER(p.placeofbirth) = LOWER(iop.placeofbirth)
                      AND LOWER(p.fullname) = LOWER(iop.fullname)
                      AND p.listname = ?1
                      AND p.datelist = ?2) as matchingRecords""", nativeQuery = true)
    Integer findMatchingRecordsCount(String listName, String dateList);
}
