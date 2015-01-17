package org.dyndns.tarotmc.g3cm.service;

import org.dyndns.tarotmc.g3cm.domain.Authority;
import org.dyndns.tarotmc.g3cm.domain.CharacterAdvantage;
import org.dyndns.tarotmc.g3cm.domain.PersistentToken;
import org.dyndns.tarotmc.g3cm.domain.User;
import org.dyndns.tarotmc.g3cm.repository.AuthorityRepository;
import org.dyndns.tarotmc.g3cm.repository.CharacterAdvantageRepository;
import org.dyndns.tarotmc.g3cm.repository.CharacterAttributeRepository;
import org.dyndns.tarotmc.g3cm.repository.CharacterRepository;
import org.dyndns.tarotmc.g3cm.repository.CharacterSkillRepository;
import org.dyndns.tarotmc.g3cm.repository.PersistentTokenRepository;
import org.dyndns.tarotmc.g3cm.repository.UserRepository;
import org.dyndns.tarotmc.g3cm.security.SecurityUtils;
import org.dyndns.tarotmc.g3cm.service.util.RandomUtil;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.swing.text.AttributeSet.CharacterAttribute;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Service class for managing users.
 */
@Service
@Transactional
public class CharacterPointService {

    private final Logger log = LoggerFactory.getLogger(CharacterPointService.class);

    @Inject
    private UserRepository userRepository;

    @Inject
    private CharacterAdvantageRepository advantageRepository;
    
    @Inject
    private CharacterAttributeRepository attributeRepository;
    
    @Inject 
    private CharacterSkillRepository skillRepository;
    
    @Inject
    private CharacterRepository characterRepository;
    
    public void CalculateUsedPoints(long characterId){
    	log.debug("Begin calulating points");
    	long attributes = attributeRepository.getPointTotalForAttributesByCharacterId(characterId);
    	log.debug("attributes: "+attributes);
    	long skills=skillRepository.getPointTotalForSkillsByCharacterId(characterId);
    	log.debug("skills: "+skills);
    	long advantages=advantageRepository.getPointTotalForAdvantagesByCharacterId(characterId);
    	log.debug("advantages: "+advantages);
    	
    	org.dyndns.tarotmc.g3cm.domain.Character character = characterRepository.getOne(characterId);
    	character.setUsedPoints(attributes+skills+advantages);
    	characterRepository.save(character);
    }

	public UserRepository getUserRepository() {
		return userRepository;
	}

	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public CharacterAdvantageRepository getAdvantageRepository() {
		return advantageRepository;
	}

	public void setAdvantageRepository(
			CharacterAdvantageRepository advantageRepository) {
		this.advantageRepository = advantageRepository;
	}

	public CharacterAttributeRepository getAttributeRepository() {
		return attributeRepository;
	}

	public void setAttributeRepository(
			CharacterAttributeRepository attributeRepository) {
		this.attributeRepository = attributeRepository;
	}

	public CharacterSkillRepository getSkillRepository() {
		return skillRepository;
	}

	public void setSkillRepository(CharacterSkillRepository skillRepository) {
		this.skillRepository = skillRepository;
	}

	public CharacterRepository getCharacterRepository() {
		return characterRepository;
	}

	public void setCharacterRepository(CharacterRepository characterRepository) {
		this.characterRepository = characterRepository;
	}
    
    
}
