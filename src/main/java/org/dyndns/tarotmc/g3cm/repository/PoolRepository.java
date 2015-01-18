package org.dyndns.tarotmc.g3cm.repository;

import org.dyndns.tarotmc.g3cm.domain.Pool;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Pool entity.
 */
public interface PoolRepository extends JpaRepository<Pool, Long> {

}
