package org.dyndns.tarotmc.g3cm.web.rest;

import com.codahale.metrics.annotation.Timed;

import org.dyndns.tarotmc.g3cm.domain.AttributeType;
import org.dyndns.tarotmc.g3cm.domain.Character;
import org.dyndns.tarotmc.g3cm.domain.CharacterAttribute;
import org.dyndns.tarotmc.g3cm.domain.User;
import org.dyndns.tarotmc.g3cm.repository.AttributeTypeRepository;
import org.dyndns.tarotmc.g3cm.repository.CharacterAttributeRepository;
import org.dyndns.tarotmc.g3cm.repository.CharacterRepository;
import org.dyndns.tarotmc.g3cm.service.CharacterPointService;
import org.dyndns.tarotmc.g3cm.service.UserService;
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
 * REST controller for managing Character.
 */
@RestController
@RequestMapping("/app")
public class CharacterResource {

	private final Logger log = LoggerFactory.getLogger(CharacterResource.class);

	@Inject
	private CharacterRepository characterRepository;
	@Inject
	UserService userService;
	@Inject
	AttributeTypeRepository attributeRepository;
	@Inject
	CharacterAttributeRepository charAttRepository;
	@Inject
	private CharacterPointService pointService;

	/**
	 * POST /rest/characters -> Create a new character.
	 */
	@RequestMapping(value = "/rest/characters", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public void create(@RequestBody Character character) {
		log.debug("REST request to save Character : {}", character);
		User currentUser = userService.getUserWithAuthorities();
		character.setUser(currentUser);
		log.debug("adding attribute");

		Boolean isNew = character.getId() == null;
		characterRepository.save(character);

		if (isNew) {
			for (AttributeType attribute : attributeRepository.findAll()) {
				log.debug("Setting attribute" + attribute.getName());
				CharacterAttribute charAtt = new CharacterAttribute();
				charAtt.setPoints(0);
				charAtt.setBonus(0);
				charAtt.setAttributeType(attribute);
				charAtt.setRating(10);
				charAtt.setCharacter(character);
				charAttRepository.save(charAtt);
				// character.getCharacterAttributes().add(charAtt);
			}
		}
	}

	/**
	 * GET /rest/characters -> get all the characters.
	 */
	@RequestMapping(value = "/rest/characters", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public List<Character> getAll() {
		log.debug("REST request to get all Characters");
		return characterRepository.findAll();
	}

	/**
	 * GET /rest/characters/:id -> get the "id" character.
	 */
	@RequestMapping(value = "/rest/characters/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Character> get(@PathVariable Long id,
			HttpServletResponse response) {
		log.debug("REST request to get Character : {}", id);
		Character character = characterRepository
				.findOneWithEagerRelationships(id);
		if (character == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(character, HttpStatus.OK);
	}

	/**
	 * DELETE /rest/characters/:id -> delete the "id" character.
	 */
	@RequestMapping(value = "/rest/characters/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public void delete(@PathVariable Long id) {
		log.debug("REST request to delete Character : {}", id);
		characterRepository.delete(id);
	}

	@RequestMapping(value = "/rest/characterpoints/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public void calculatePoints(@PathVariable Long id) {
		pointService.CalculateUsedPoints(id);
	}
}
