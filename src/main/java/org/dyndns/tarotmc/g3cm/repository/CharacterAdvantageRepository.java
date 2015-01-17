package org.dyndns.tarotmc.g3cm.repository;

import java.util.List;

import org.dyndns.tarotmc.g3cm.domain.CharacterAdvantage;
import org.dyndns.tarotmc.g3cm.domain.CharacterAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Spring Data JPA repository for the CharacterAdvantage entity.
 */
public interface CharacterAdvantageRepository extends JpaRepository<CharacterAdvantage, Long> {
	public List<CharacterAdvantage>findAllByCharacterId(long characterId);

	@Query("select coalesce(sum(a.points),0) from CharacterAdvantage a where a.character.id = ?1")
	public Long getPointTotalForAdvantagesByCharacterId(long characterId);
	
}
