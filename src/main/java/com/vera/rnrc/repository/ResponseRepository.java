package com.vera.rnrc.repository;

import com.vera.rnrc.entity.ResponseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResponseRepository extends JpaRepository<ResponseEntity, Long> {

    @Query(value = "select r.id          as partner_id, " +
            "       'Контрагент'  as check_object, " +
            "       null          as linked_structure_order, " +
            "       null          as participant_order, " +
            "       r.partnername as short_name, " +
            "       CASE " +
            "           WHEN l.id is not null " +
            "               THEN CONCAT('Идентифицирован по перечню от ') " +
            "           ELSE CONCAT('Не идентифицирован по перечню от ') " +
            "           END       as check_result, " +
            "       l.id          as list_id " +
            "FROM rnrc_ref_partner r " +
            "         left JOIN viewLegal l ON r.id = l.partner_id;", nativeQuery = true)
    List<ResponseEntity> findLegalPersonResult();

    @Query(value = "select iop.id                     as partner_id, " +
            "       CASE iop.linkedstructuretype " +
            "           WHEN 'Beneficiary' THEN 'Бенефициар' " +
            "           WHEN 'BenefitHolder' THEN 'Выгодоприобретатель' " +
            "           WHEN 'ManagementBody' THEN 'Управляющий орган' " +
            "           WHEN 'Representative' THEN 'Представитель' " +
            "           ELSE 'Контрагент' " +
            "           END                    as check_object, " +
            "       max(CASE " +
            "               WHEN iop.linkedstructuretype = 'ManagementBody' THEN iop.managementbodyordernumber " +
            "               ELSE iop.participantordernumber " +
            "           END)                   as linked_structure_order, " +
            "       iop.participantordernumber as participant_order, " +
            "       max(CASE " +
            "               WHEN CHAR_LENGTH(TRIM(CONCAT(iop.lastname, ' ', iop.firstname, ' ', iop.middlename))) < 2 " +
            "                   THEN SPLIT_PART(fullname, ', ', 2) " +
            "               ELSE TRIM(CONCAT(iop.lastname, ' ', iop.firstname, ' ', iop.middlename)) " +
            "           END)                   as short_name, " +
            "       max(CASE " +
            "               WHEN v.id is not null " +
            "                   THEN CONCAT('Идентифицирован по перечню от ') " +
            "               ELSE CONCAT('Не идентифицирован по перечню от ') " +
            "           END)                   as check_result, " +
            "       max(v.id)                  as list_id " +
            "FROM index_olr_partnerlinkedstructure iop " +
            "         left join viewPhysical v on v.partner_id = iop.pzinskey " +
            "group by iop.id, iop.participantordernumber, iop.managementbodyordernumber, iop.linkedstructuretype;", nativeQuery = true)
    List<ResponseEntity> findPhisicalPersonResult();
    @Query(value = "select * from response;", nativeQuery = true)
    List<ResponseEntity> findPhisicalPersonResult2();

    @Query(value =
            "select r.id          as partner_id, " +
                    "       'Контрагент'  as check_object, " +
                    "       null          as linked_structure_order, " +
                    "       null          as participant_order, " +
                    "       r.partnername as short_name, " +
                    "       Max(CASE " +
                    "               WHEN l.id is not null " +
                    "                   THEN CONCAT('Идентифицирован по перечню от ') " +
                    "               ELSE CONCAT('Не идентифицирован по перечню от ') " +
                    "           END  )     as check_result, " +
                    "       max(l.id)          as list_id " +
                    "FROM rnrc_ref_partner r " +
                    "         left JOIN viewLegal l ON r.id = l.partner_id " +
                    "group by r.id, r.partnername " +
                    "union " +
                    "select iop.id                     as partner_id, " +
                    "       CASE iop.linkedstructuretype " +
                    "           WHEN 'Beneficiary' THEN 'Бенефициар' " +
                    "           WHEN 'BenefitHolder' THEN 'Выгодоприобретатель' " +
                    "           WHEN 'ManagementBody' THEN 'Управляющий орган' " +
                    "           WHEN 'Representative' THEN 'Представитель' " +
                    "           ELSE 'Контрагент' " +
                    "           END                    as check_object, " +
                    "       max(CASE " +
                    "               WHEN iop.linkedstructuretype = 'ManagementBody' THEN iop.managementbodyordernumber " +
                    "               ELSE iop.participantordernumber " +
                    "           END)                   as linked_structure_order, " +
                    "       iop.participantordernumber as participant_order, " +
                    "       max(CASE " +
                    "               WHEN CHAR_LENGTH(TRIM(CONCAT(iop.lastname, ' ', iop.firstname, ' ', iop.middlename))) < 2 " +
                    "                   THEN SPLIT_PART(fullname, ', ', 2) " +
                    "               ELSE TRIM(CONCAT(iop.lastname, ' ', iop.firstname, ' ', iop.middlename)) " +
                    "           END)                   as short_name, " +
                    "       max(CASE " +
                    "               WHEN v.id is not null " +
                    "                   THEN CONCAT('Идентифицирован по перечню от ') " +
                    "               ELSE CONCAT('Не идентифицирован по перечню от ') " +
                    "           END)                   as check_result, " +
                    "       max(v.id)                  as list_id " +
                    "FROM index_olr_partnerlinkedstructure iop " +
                    "         left join viewPhysical v on v.partner_id = iop.pzinskey " +
                    "group by iop.id, iop.participantordernumber, iop.managementbodyordernumber, iop.linkedstructuretype ;", nativeQuery = true)
    List<ResponseEntity> findAllPerson();

    @Query(value = "CREATE VIEW viewName AS " +
            "SELECT p.id " +
            "FROM index_olr_partnerlinkedstructure iop " +
            "         JOIN physical_person p ON p.inn = iop.inn " +
            "WHERE p.inn = iop.inn " +
            "   OR (p.passport_series = iop.docseries AND p.passport_number = iop.docnumber) " +
            "   OR (p.date_of_birth = iop.dateofbirth AND LOWER(p.surname) = LOWER(iop.lastname) AND " +
            "       LOWER(p.name) = LOWER(iop.firstname) AND (p.patronymic) = (iop.middlename)) " +
            "   OR (p.date_of_birth = iop.dateofbirth AND LOWER(p.full_name) = LOWER(iop.fullname)) " +
            "   OR (p.id = iop.id) and p.date_list = :dateList  AND p.list_name = :listName ;", nativeQuery = true)
    void createView(@Param("dateList") String dateList, @Param("listName") String listName);

    @Query(value = "drop view viewName;", nativeQuery = true)
    void dropView();
}
