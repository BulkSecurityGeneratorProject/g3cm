package org.dyndns.tarotmc.g3cm.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.dyndns.tarotmc.g3cm.domain.Advantage;
import org.dyndns.tarotmc.g3cm.repository.AdvantageRepository;
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
 * REST controller for managing Advantage.
 */
@RestController
@RequestMapping("/app")
public class AdvantageResource {

    private final Logger log = LoggerFactory.getLogger(AdvantageResource.class);

    @Inject
    private AdvantageRepository advantageRepository;

    /**
     * POST  /rest/advantages -> Create a new advantage.
     */
    @RequestMapping(value = "/rest/advantages",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Advantage advantage) {
        log.debug("REST request to save Advantage : {}", advantage);
        advantageRepository.save(advantage);
    }

    /**
     * GET  /rest/advantages -> get all the advantages.
     */
    @RequestMapping(value = "/rest/advantages",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Advantage> getAll() {
        log.debug("REST request to get all Advantages");
        return advantageRepository.findAll();
    }

    /**
     * GET  /rest/advantages/:id -> get the "id" advantage.
     */
    @RequestMapping(value = "/rest/advantages/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Advantage> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Advantage : {}", id);
        Advantage advantage = advantageRepository.findOne(id);
        if (advantage == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(advantage, HttpStatus.OK);
    }

    /**
     * DELETE  /rest/advantages/:id -> delete the "id" advantage.
     */
    @RequestMapping(value = "/rest/advantages/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Advantage : {}", id);
        advantageRepository.delete(id);
    }
}
