package org.dyndns.tarotmc.g3cm.repository;

import java.util.List;

import org.dyndns.tarotmc.g3cm.domain.Character;
import org.dyndns.tarotmc.g3cm.domain.CharacterAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Spring Data JPA repository for the CharacterAttribute entity.
 */
public interface CharacterAttributeRepository extends JpaRepository<CharacterAttribute, Long> {
	public List<CharacterAttribute>findAllByCharacterId(long characterId);
	
	@Query("select coalesce(sum(a.points),0) from CharacterAttribute a where a.character.id = ?1")
	public Long getPointTotalForAttributesByCharacterId(long characterId);
	
}
