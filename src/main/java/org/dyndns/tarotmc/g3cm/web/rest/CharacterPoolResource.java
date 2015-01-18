package org.dyndns.tarotmc.g3cm.web.rest;

import com.codahale.metrics.annotation.Timed;

import org.dyndns.tarotmc.g3cm.domain.CharacterAttribute;
import org.dyndns.tarotmc.g3cm.domain.CharacterPool;
import org.dyndns.tarotmc.g3cm.repository.CharacterPoolRepository;
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
 * REST controller for managing CharacterPool.
 */
@RestController
@RequestMapping("/app")
public class CharacterPoolResource {

    private final Logger log = LoggerFactory.getLogger(CharacterPoolResource.class);

    @Inject
    private CharacterPoolRepository characterPoolRepository;

    /**
     * POST  /rest/characterPools -> Create a new characterPool.
     */
    @RequestMapping(value = "/rest/characterPools",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody CharacterPool characterPool) {
        log.debug("REST request to save CharacterPool : {}", characterPool);
        characterPoolRepository.save(characterPool);
    }
    
    /**
     * put  /rest/characterPools -> Create a new characterPool.
     */
    @RequestMapping(value = "/rest/characterPools",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void update(@RequestBody CharacterPool characterPool) {
        log.debug("REST request to update CharacterPool : {}", characterPool);
        characterPoolRepository.save(characterPool);
    }

    /**
     * GET  /rest/characterPools -> get all the characterPools.
     */
    @RequestMapping(value = "/rest/characterPools",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<CharacterPool> getAll() {
        log.debug("REST request to get all CharacterPools");
        return characterPoolRepository.findAll();
    }

    /**
     * GET  /rest/characterPools/:id -> get the "id" characterPool.
     */
    @RequestMapping(value = "/rest/characterPools/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CharacterPool> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get CharacterPool : {}", id);
        CharacterPool characterPool = characterPoolRepository.findOne(id);
        if (characterPool == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(characterPool, HttpStatus.OK);
    }

    /**
     * DELETE  /rest/characterPools/:id -> delete the "id" characterPool.
     */
    @RequestMapping(value = "/rest/characterPools/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete CharacterPool : {}", id);
        characterPoolRepository.delete(id);
    }
    
    /**
     * GET  /rest/characterAttributes -> get all the characterAttributes.
     */
    @RequestMapping(value = "/rest/characters/{characterid}/characterPools",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<CharacterPool> getAllByCharacterId(@PathVariable Long characterid, HttpServletResponse response) {
        log.debug("REST request to get CharacterPools with characterId : {}", characterid);
        return characterPoolRepository.findAllByCharacterId(characterid);
    }
}
