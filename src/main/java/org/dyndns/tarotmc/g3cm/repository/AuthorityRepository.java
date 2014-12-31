package org.dyndns.tarotmc.g3cm.repository;

import org.dyndns.tarotmc.g3cm.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
