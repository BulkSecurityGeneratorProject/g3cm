package org.dyndns.tarotmc.g3cm.repository;

import org.dyndns.tarotmc.g3cm.domain.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Campaign entity.
 */
public interface CampaignRepository extends JpaRepository<Campaign, Long> {

}
