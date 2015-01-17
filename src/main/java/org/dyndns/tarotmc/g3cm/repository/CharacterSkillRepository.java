package org.dyndns.tarotmc.g3cm.repository;

import java.util.List;

import org.dyndns.tarotmc.g3cm.domain.CharacterSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Spring Data JPA repository for the CharacterSkill entity.
 */
public interface CharacterSkillRepository extends JpaRepository<CharacterSkill, Long> {
	public List<CharacterSkill>findAllByCharacterId(long characterId);
	
	@Query("select coalesce(sum(s.points),0) from CharacterSkill s where s.character.id = ?1")
	public Long getPointTotalForSkillsByCharacterId(long characterId);
}
