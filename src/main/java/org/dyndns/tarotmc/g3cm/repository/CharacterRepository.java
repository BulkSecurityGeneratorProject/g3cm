package org.dyndns.tarotmc.g3cm.repository;

import org.dyndns.tarotmc.g3cm.domain.Character;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Character entity.
 */
public interface CharacterRepository extends JpaRepository<Character, Long> {

}
