package com.vera.rnrc.repository;

import com.vera.rnrc.entity.LegalPersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LegalPersonRepository extends JpaRepository<LegalPersonEntity, Long> {
}