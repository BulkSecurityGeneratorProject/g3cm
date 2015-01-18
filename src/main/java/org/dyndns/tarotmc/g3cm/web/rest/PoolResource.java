package org.dyndns.tarotmc.g3cm.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.dyndns.tarotmc.g3cm.domain.Pool;
import org.dyndns.tarotmc.g3cm.repository.PoolRepository;
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
 * REST controller for managing Pool.
 */
@RestController
@RequestMapping("/app")
public class PoolResource {

    private final Logger log = LoggerFactory.getLogger(PoolResource.class);

    @Inject
    private PoolRepository poolRepository;

    /**
     * POST  /rest/pools -> Create a new pool.
     */
    @RequestMapping(value = "/rest/pools",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Pool pool) {
        log.debug("REST request to save Pool : {}", pool);
        poolRepository.save(pool);
    }

    /**
     * GET  /rest/pools -> get all the pools.
     */
    @RequestMapping(value = "/rest/pools",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Pool> getAll() {
        log.debug("REST request to get all Pools");
        return poolRepository.findAll();
    }

    /**
     * GET  /rest/pools/:id -> get the "id" pool.
     */
    @RequestMapping(value = "/rest/pools/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Pool> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Pool : {}", id);
        Pool pool = poolRepository.findOne(id);
        if (pool == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(pool, HttpStatus.OK);
    }

    /**
     * DELETE  /rest/pools/:id -> delete the "id" pool.
     */
    @RequestMapping(value = "/rest/pools/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Pool : {}", id);
        poolRepository.delete(id);
    }
}
