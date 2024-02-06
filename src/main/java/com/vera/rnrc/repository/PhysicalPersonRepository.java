package com.vera.rnrc.repository;

import com.vera.rnrc.entity.PhysicalPersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhysicalPersonRepository extends JpaRepository<PhysicalPersonEntity, Long> {
}