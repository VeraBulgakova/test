package com.vera.rnrc.repository;

import com.vera.rnrc.entity.PartnerLegalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnerLegalRepository extends JpaRepository<PartnerLegalEntity, Long> {
}
