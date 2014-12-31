package org.dyndns.tarotmc.g3cm.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.dyndns.tarotmc.g3cm.domain.FormSkillMod;
import org.dyndns.tarotmc.g3cm.repository.FormSkillModRepository;
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
 * REST controller for managing FormSkillMod.
 */
@RestController
@RequestMapping("/app")
public class FormSkillModResource {

    private final Logger log = LoggerFactory.getLogger(FormSkillModResource.class);

    @Inject
    private FormSkillModRepository formSkillModRepository;

    /**
     * POST  /rest/formSkillMods -> Create a new formSkillMod.
     */
    @RequestMapping(value = "/rest/formSkillMods",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody FormSkillMod formSkillMod) {
        log.debug("REST request to save FormSkillMod : {}", formSkillMod);
        formSkillModRepository.save(formSkillMod);
    }

    /**
     * GET  /rest/formSkillMods -> get all the formSkillMods.
     */
    @RequestMapping(value = "/rest/formSkillMods",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<FormSkillMod> getAll() {
        log.debug("REST request to get all FormSkillMods");
        return formSkillModRepository.findAll();
    }

    /**
     * GET  /rest/formSkillMods/:id -> get the "id" formSkillMod.
     */
    @RequestMapping(value = "/rest/formSkillMods/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FormSkillMod> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get FormSkillMod : {}", id);
        FormSkillMod formSkillMod = formSkillModRepository.findOne(id);
        if (formSkillMod == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(formSkillMod, HttpStatus.OK);
    }

    /**
     * DELETE  /rest/formSkillMods/:id -> delete the "id" formSkillMod.
     */
    @RequestMapping(value = "/rest/formSkillMods/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete FormSkillMod : {}", id);
        formSkillModRepository.delete(id);
    }
}
