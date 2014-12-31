package org.dyndns.tarotmc.g3cm.web.rest;

import java.util.List;

import com.codahale.metrics.annotation.Timed;

import org.dyndns.tarotmc.g3cm.domain.User;
import org.dyndns.tarotmc.g3cm.repository.UserRepository;
import org.dyndns.tarotmc.g3cm.security.AuthoritiesConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

/**
 * REST controller for managing users.
 */
@RestController
@RequestMapping("/app")
public class UserResource {

	private final Logger log = LoggerFactory.getLogger(UserResource.class);

	@Inject
	private UserRepository userRepository;

	/**
	 * GET /rest/users/:login -> get the "login" user.
	 */
	@RequestMapping(value = "/rest/users/{login}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.ADMIN)
	public User getUser(@PathVariable String login, HttpServletResponse response) {
		log.debug("REST request to get User : {}", login);
		User user = userRepository.findOne(login);
		if (user == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
		return user;
	}

	/**
	 * GET /rest/user -> get all the users.
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/rest/users", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public List<User> getAll() {
		log.debug("REST request to get all Users");
		return userRepository.findAll();
	}

	/**
	 * POST /rest/user -> Create a new user.
	 */
	@RequestMapping(value = "/rest/user", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public void create(@RequestBody User user) {
		log.debug("REST request to save User : {}", user);
		userRepository.save(user);
	}
}
