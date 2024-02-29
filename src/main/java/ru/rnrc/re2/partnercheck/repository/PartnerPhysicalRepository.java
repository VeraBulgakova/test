package ru.rnrc.re2.partnercheck.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.rnrc.re2.partnercheck.entity.PartnerPhysical;

@Repository
public interface PartnerPhysicalRepository extends JpaRepository<PartnerPhysical, Long> {
}
