package org.dyndns.tarotmc.g3cm.web.rest;

import com.codahale.metrics.annotation.Timed;

import org.dyndns.tarotmc.g3cm.domain.CharacterAdvantage;
import org.dyndns.tarotmc.g3cm.domain.CharacterAttribute;
import org.dyndns.tarotmc.g3cm.repository.CharacterAdvantageRepository;
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
 * REST controller for managing CharacterAdvantage.
 */
@RestController
@RequestMapping("/app")
public class CharacterAdvantageResource {

    private final Logger log = LoggerFactory.getLogger(CharacterAdvantageResource.class);

    @Inject
    private CharacterAdvantageRepository characterAdvantageRepository;

    /**
     * POST  /rest/characterAdvantages -> Create a new characterAdvantage.
     */
    @RequestMapping(value = "/rest/characterAdvantages",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody CharacterAdvantage characterAdvantage) {
        log.debug("REST request to save CharacterAdvantage : {}", characterAdvantage);
        characterAdvantageRepository.save(characterAdvantage);
    }

    /**
     * GET  /rest/characterAdvantages -> get all the characterAdvantages.
     */
    @RequestMapping(value = "/rest/characterAdvantages",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<CharacterAdvantage> getAll() {
        log.debug("REST request to get all CharacterAdvantages");
        return characterAdvantageRepository.findAll();
    }
    
    /**
     * GET  /rest/characterAttributes -> get all the characterAttributes.
     */
    @RequestMapping(value = "/rest/character/{characterid}/characterAdvantages",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<CharacterAdvantage> getAllByCharacterId(@PathVariable Long characterid, HttpServletResponse response) {
        log.debug("REST request to get CharacterAttribute with characterId : {}", characterid);
        return characterAdvantageRepository.findAllByCharacterId(characterid);
    }

    /**
     * GET  /rest/characterAdvantages/:id -> get the "id" characterAdvantage.
     */
    @RequestMapping(value = "/rest/characterAdvantages/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CharacterAdvantage> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get CharacterAdvantage : {}", id);
        CharacterAdvantage characterAdvantage = characterAdvantageRepository.findOne(id);
        if (characterAdvantage == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(characterAdvantage, HttpStatus.OK);
    }

    /**
     * DELETE  /rest/characterAdvantages/:id -> delete the "id" characterAdvantage.
     */
    @RequestMapping(value = "/rest/characterAdvantages/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete CharacterAdvantage : {}", id);
        characterAdvantageRepository.delete(id);
    }
}
