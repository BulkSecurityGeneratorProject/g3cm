package org.dyndns.tarotmc.g3cm.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.dyndns.tarotmc.g3cm.domain.Skill;
import org.dyndns.tarotmc.g3cm.repository.SkillRepository;
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
 * REST controller for managing Skill.
 */
@RestController
@RequestMapping("/app")
public class SkillResource {

    private final Logger log = LoggerFactory.getLogger(SkillResource.class);

    @Inject
    private SkillRepository skillRepository;

    /**
     * POST  /rest/skills -> Create a new skill.
     */
    @RequestMapping(value = "/rest/skills",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Skill skill) {
        log.debug("REST request to save Skill : {}", skill);
        skillRepository.save(skill);
    }

    /**
     * GET  /rest/skills -> get all the skills.
     */
    @RequestMapping(value = "/rest/skills",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Skill> getAll() {
        log.debug("REST request to get all Skills");
        return skillRepository.findAll();
    }

    /**
     * GET  /rest/skills/:id -> get the "id" skill.
     */
    @RequestMapping(value = "/rest/skills/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Skill> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Skill : {}", id);
        Skill skill = skillRepository.findOne(id);
        if (skill == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(skill, HttpStatus.OK);
    }

    /**
     * DELETE  /rest/skills/:id -> delete the "id" skill.
     */
    @RequestMapping(value = "/rest/skills/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Skill : {}", id);
        skillRepository.delete(id);
    }
}
