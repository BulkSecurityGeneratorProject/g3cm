package org.dyndns.tarotmc.g3cm.repository;

import org.dyndns.tarotmc.g3cm.domain.Form;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data JPA repository for the Form entity.
 */
public interface FormRepository extends JpaRepository<Form, Long> {

    @Query("select form from Form form left join fetch form.advantages where form.id = :id")
    Form findOneWithEagerRelationships(@Param("id") Long id);

}
