package org.dyndns.tarotmc.g3cm.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.dyndns.tarotmc.g3cm.domain.AttributeType;
import org.dyndns.tarotmc.g3cm.repository.AttributeTypeRepository;
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
 * REST controller for managing AttributeType.
 */
@RestController
@RequestMapping("/app")
public class AttributeTypeResource {

    private final Logger log = LoggerFactory.getLogger(AttributeTypeResource.class);

    @Inject
    private AttributeTypeRepository attributeTypeRepository;

    /**
     * POST  /rest/attributeTypes -> Create a new attributeType.
     */
    @RequestMapping(value = "/rest/attributeTypes",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody AttributeType attributeType) {
        log.debug("REST request to save AttributeType : {}", attributeType);
        attributeTypeRepository.save(attributeType);
    }

    /**
     * GET  /rest/attributeTypes -> get all the attributeTypes.
     */
    @RequestMapping(value = "/rest/attributeTypes",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AttributeType> getAll() {
        log.debug("REST request to get all AttributeTypes");
        return attributeTypeRepository.findAll();
    }

    /**
     * GET  /rest/attributeTypes/:id -> get the "id" attributeType.
     */
    @RequestMapping(value = "/rest/attributeTypes/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AttributeType> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get AttributeType : {}", id);
        AttributeType attributeType = attributeTypeRepository.findOne(id);
        if (attributeType == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(attributeType, HttpStatus.OK);
    }

    /**
     * DELETE  /rest/attributeTypes/:id -> delete the "id" attributeType.
     */
    @RequestMapping(value = "/rest/attributeTypes/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete AttributeType : {}", id);
        attributeTypeRepository.delete(id);
    }
}
