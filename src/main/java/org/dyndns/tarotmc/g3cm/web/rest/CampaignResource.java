package org.dyndns.tarotmc.g3cm.web.rest;

import com.codahale.metrics.annotation.Timed;

import org.dyndns.tarotmc.g3cm.domain.Campaign;
import org.dyndns.tarotmc.g3cm.domain.User;
import org.dyndns.tarotmc.g3cm.repository.CampaignRepository;
import org.dyndns.tarotmc.g3cm.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

/**
 * REST controller for managing Campaign.
 */
@RestController
@RequestMapping("/app")
public class CampaignResource {

    private final Logger log = LoggerFactory.getLogger(CampaignResource.class);

    @Inject
    private CampaignRepository campaignRepository;
    @Inject 
    UserService userService;

    /**
     * POST  /rest/campaigns -> Create a new campaign.
     */
    @RequestMapping(value = "/rest/campaigns",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Campaign campaign) {
        log.debug("REST request to save Campaign : {}", campaign);
        User currentUser = userService.getUserWithAuthorities();
        campaign.setUser(currentUser);
        campaignRepository.save(campaign);
    }

    /**
     * GET  /rest/campaigns -> get all the campaigns.
     */
    @RequestMapping(value = "/rest/campaigns",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Campaign> getAll() {
        log.debug("REST request to get all Campaigns");
        return campaignRepository.findAll();
    }

    /**
     * GET  /rest/campaigns/:id -> get the "id" campaign.
     */
    @RequestMapping(value = "/rest/campaigns/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Campaign> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Campaign : {}", id);
        Campaign campaign = campaignRepository.findOne(id);
        if (campaign == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(campaign, HttpStatus.OK);
    }

    /**
     * DELETE  /rest/campaigns/:id -> delete the "id" campaign.
     */
    @RequestMapping(value = "/rest/campaigns/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Campaign : {}", id);
        campaignRepository.delete(id);
    }
}
