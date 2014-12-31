package org.dyndns.tarotmc.g3cm.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.dyndns.tarotmc.g3cm.domain.Form;
import org.dyndns.tarotmc.g3cm.repository.FormRepository;
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
 * REST controller for managing Form.
 */
@RestController
@RequestMapping("/app")
public class FormResource {

    private final Logger log = LoggerFactory.getLogger(FormResource.class);

    @Inject
    private FormRepository formRepository;

    /**
     * POST  /rest/forms -> Create a new form.
     */
    @RequestMapping(value = "/rest/forms",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Form form) {
        log.debug("REST request to save Form : {}", form);
        formRepository.save(form);
    }

    /**
     * GET  /rest/forms -> get all the forms.
     */
    @RequestMapping(value = "/rest/forms",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Form> getAll() {
        log.debug("REST request to get all Forms");
        return formRepository.findAll();
    }

    /**
     * GET  /rest/forms/:id -> get the "id" form.
     */
    @RequestMapping(value = "/rest/forms/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Form> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Form : {}", id);
        Form form = formRepository.findOneWithEagerRelationships(id);
        if (form == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(form, HttpStatus.OK);
    }

    /**
     * DELETE  /rest/forms/:id -> delete the "id" form.
     */
    @RequestMapping(value = "/rest/forms/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Form : {}", id);
        formRepository.delete(id);
    }
}
