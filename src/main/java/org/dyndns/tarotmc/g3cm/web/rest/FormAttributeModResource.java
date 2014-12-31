package org.dyndns.tarotmc.g3cm.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.dyndns.tarotmc.g3cm.domain.FormAttributeMod;
import org.dyndns.tarotmc.g3cm.repository.FormAttributeModRepository;
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
 * REST controller for managing FormAttributeMod.
 */
@RestController
@RequestMapping("/app")
public class FormAttributeModResource {

    private final Logger log = LoggerFactory.getLogger(FormAttributeModResource.class);

    @Inject
    private FormAttributeModRepository formAttributeModRepository;

    /**
     * POST  /rest/formAttributeMods -> Create a new formAttributeMod.
     */
    @RequestMapping(value = "/rest/formAttributeMods",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody FormAttributeMod formAttributeMod) {
        log.debug("REST request to save FormAttributeMod : {}", formAttributeMod);
        formAttributeModRepository.save(formAttributeMod);
    }

    /**
     * GET  /rest/formAttributeMods -> get all the formAttributeMods.
     */
    @RequestMapping(value = "/rest/formAttributeMods",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<FormAttributeMod> getAll() {
        log.debug("REST request to get all FormAttributeMods");
        return formAttributeModRepository.findAll();
    }

    /**
     * GET  /rest/formAttributeMods/:id -> get the "id" formAttributeMod.
     */
    @RequestMapping(value = "/rest/formAttributeMods/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FormAttributeMod> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get FormAttributeMod : {}", id);
        FormAttributeMod formAttributeMod = formAttributeModRepository.findOne(id);
        if (formAttributeMod == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(formAttributeMod, HttpStatus.OK);
    }

    /**
     * DELETE  /rest/formAttributeMods/:id -> delete the "id" formAttributeMod.
     */
    @RequestMapping(value = "/rest/formAttributeMods/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete FormAttributeMod : {}", id);
        formAttributeModRepository.delete(id);
    }
}
