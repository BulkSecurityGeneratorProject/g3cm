package org.dyndns.tarotmc.g3cm.web.rest;

import com.codahale.metrics.annotation.Timed;

import org.dyndns.tarotmc.g3cm.domain.CharacterAttribute;
import org.dyndns.tarotmc.g3cm.repository.CharacterAttributeRepository;
import org.dyndns.tarotmc.g3cm.service.CharacterPointService;
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
 * REST controller for managing CharacterAttribute.
 */
@RestController
@RequestMapping("/app")
public class CharacterAttributeResource {

    private final Logger log = LoggerFactory.getLogger(CharacterAttributeResource.class);

    @Inject
    private CharacterAttributeRepository characterAttributeRepository;
    

    /**
     * POST  /rest/characterAttributes -> Create a new characterAttribute.
     */
    @RequestMapping(value = "/rest/characterAttributes",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody CharacterAttribute characterAttribute) {
        log.debug("REST request to save CharacterAttribute : {}", characterAttribute);
        characterAttributeRepository.save(characterAttribute);
//        pointService.CalculateUsedPoints(characterAttribute.getCharacter().getId());
    }

    /**
     * GET  /rest/characterAttributes -> get all the characterAttributes.
     */
    @RequestMapping(value = "/rest/characterAttributes",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<CharacterAttribute> getAll() {
        log.debug("REST request to get all CharacterAttributes");
        return characterAttributeRepository.findAll();
    }
    
    /**
     * GET  /rest/characterAttributes -> get all the characterAttributes.
     */
    @RequestMapping(value = "/rest/characters/{characterid}/characterAttributes",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<CharacterAttribute> getAllByCharacterId(@PathVariable Long characterid, HttpServletResponse response) {
        log.debug("REST request to get CharacterAttribute with characterId : {}", characterid);
        return characterAttributeRepository.findAllByCharacterId(characterid);
    }

    /**
     * GET  /rest/characterAttributes/:id -> get the "id" characterAttribute.
     */
    @RequestMapping(value = "/rest/characterAttributes/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CharacterAttribute> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get CharacterAttribute : {}", id);
        CharacterAttribute characterAttribute = characterAttributeRepository.findOne(id);
        if (characterAttribute == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(characterAttribute, HttpStatus.OK);
    }

    /**
     * DELETE  /rest/characterAttributes/:id -> delete the "id" characterAttribute.
     */
    @RequestMapping(value = "/rest/characterAttributes/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete CharacterAttribute : {}", id);
//        long characterId = characterAttributeRepository.getOne(id).getId();
//        log.debug("Characterid:"+characterId);
        characterAttributeRepository.delete(id);
//        pointService.CalculateUsedPoints(characterId);
    }
}
