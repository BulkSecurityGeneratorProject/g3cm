package org.dyndns.tarotmc.g3cm.repository;

import org.dyndns.tarotmc.g3cm.domain.CharacterAttribute;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the CharacterAttribute entity.
 */
public interface CharacterAttributeRepository extends JpaRepository<CharacterAttribute, Long> {

}
