package org.dyndns.tarotmc.g3cm.web.rest;

import com.codahale.metrics.annotation.Timed;

import org.dyndns.tarotmc.g3cm.domain.CharacterSkill;
import org.dyndns.tarotmc.g3cm.repository.CharacterSkillRepository;
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
 * REST controller for managing CharacterSkill.
 */
@RestController
@RequestMapping("/app")
public class CharacterSkillResource {

    private final Logger log = LoggerFactory.getLogger(CharacterSkillResource.class);

    @Inject
    private CharacterSkillRepository characterSkillRepository;

    /**
     * POST  /rest/characterSkills -> Create a new characterSkill.
     */
    @RequestMapping(value = "/rest/characterSkills",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody CharacterSkill characterSkill) {
        log.debug("REST request to save CharacterSkill : {}", characterSkill);
        characterSkillRepository.save(characterSkill);
    }
    
    /**
     * GET  /rest/characterSkills -> get all the characterSkills.
     */
    @RequestMapping(value = "/rest/characters/{characterid}/characterSkills",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<CharacterSkill> getAllForCharacter(@PathVariable Long characterid, HttpServletResponse response) {
        log.debug("REST request to get all CharacterSkills");
        return characterSkillRepository.findAllByCharacterId(characterid);
    }

    /**
     * GET  /rest/characterSkills -> get all the characterSkills.
     */
    @RequestMapping(value = "/rest/characterSkills",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<CharacterSkill> getAll() {
        log.debug("REST request to get all CharacterSkills");
        return characterSkillRepository.findAll();
    }

    /**
     * GET  /rest/characterSkills/:id -> get the "id" characterSkill.
     */
    @RequestMapping(value = "/rest/characterSkills/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CharacterSkill> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get CharacterSkill : {}", id);
        CharacterSkill characterSkill = characterSkillRepository.findOne(id);
        if (characterSkill == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(characterSkill, HttpStatus.OK);
    }

    /**
     * DELETE  /rest/characterSkills/:id -> delete the "id" characterSkill.
     */
    @RequestMapping(value = "/rest/characterSkills/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete CharacterSkill : {}", id);
        characterSkillRepository.delete(id);
    }
}
