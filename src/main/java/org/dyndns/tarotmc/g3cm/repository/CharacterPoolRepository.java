package org.dyndns.tarotmc.g3cm.repository;

import java.util.List;

import org.dyndns.tarotmc.g3cm.domain.CharacterPool;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the CharacterPool entity.
 */
public interface CharacterPoolRepository extends JpaRepository<CharacterPool, Long> {
	public List<CharacterPool>findAllByCharacterId(long characterId);
}
