package org.dyndns.tarotmc.g3cm.repository;

import java.util.List;

import org.dyndns.tarotmc.g3cm.domain.CharacterSkill;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the CharacterSkill entity.
 */
public interface CharacterSkillRepository extends JpaRepository<CharacterSkill, Long> {
	public List<CharacterSkill>findAllByCharacterId(long characterId);
}
