package org.dyndns.tarotmc.g3cm.repository;

import java.util.List;

import org.dyndns.tarotmc.g3cm.domain.CharacterAdvantage;
import org.dyndns.tarotmc.g3cm.domain.CharacterAttribute;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the CharacterAdvantage entity.
 */
public interface CharacterAdvantageRepository extends JpaRepository<CharacterAdvantage, Long> {
	public List<CharacterAdvantage>findAllByCharacterId(long characterId);
}
