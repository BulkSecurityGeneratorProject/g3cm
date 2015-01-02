package org.dyndns.tarotmc.g3cm.repository;

import org.dyndns.tarotmc.g3cm.domain.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data JPA repository for the Character entity.
 */
public interface CharacterRepository extends JpaRepository<Character, Long> {

    @Query("select character from Character character left join fetch character.forms where character.id = :id")
    Character findOneWithEagerRelationships(@Param("id") Long id);

}
