package csd214.app.repositories;

import csd214.app.entities.SaleLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleLogRepository extends JpaRepository<SaleLogEntity, Long> {}

