package com.vera.rnrc.repository;

import com.vera.rnrc.entity.PartnerPhysicalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnerPhysicalRepository extends JpaRepository<PartnerPhysicalEntity, Long> {
}
