package org.dyndns.tarotmc.g3cm.web.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.List;

import org.dyndns.tarotmc.g3cm.Application;
import org.dyndns.tarotmc.g3cm.domain.CharacterSkill;
import org.dyndns.tarotmc.g3cm.repository.CharacterSkillRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CharacterSkillResource REST controller.
 *
 * @see CharacterSkillResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class CharacterSkillResourceTest {

    private static final Integer DEFAULT_BONUS = 0;
    private static final Integer UPDATED_BONUS = 1;
    
    private static final BigDecimal DEFAULT_POINTS = BigDecimal.ZERO;
    private static final BigDecimal UPDATED_POINTS = BigDecimal.ONE;
    
    private static final Integer DEFAULT_LEVEL = 0;
    private static final Integer UPDATED_LEVEL = 1;
    

    @Inject
    private CharacterSkillRepository characterSkillRepository;

    private MockMvc restCharacterSkillMockMvc;

    private CharacterSkill characterSkill;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CharacterSkillResource characterSkillResource = new CharacterSkillResource();
        ReflectionTestUtils.setField(characterSkillResource, "characterSkillRepository", characterSkillRepository);
        this.restCharacterSkillMockMvc = MockMvcBuilders.standaloneSetup(characterSkillResource).build();
    }

    @Before
    public void initTest() {
        characterSkill = new CharacterSkill();
        characterSkill.setBonus(DEFAULT_BONUS);
        characterSkill.setPoints(DEFAULT_POINTS);
        characterSkill.setLevel(DEFAULT_LEVEL);
    }

    @Test
    @Transactional
    public void createCharacterSkill() throws Exception {
        // Validate the database is empty
        assertThat(characterSkillRepository.findAll()).hasSize(0);

        // Create the CharacterSkill
        restCharacterSkillMockMvc.perform(post("/app/rest/characterSkills")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(characterSkill)))
                .andExpect(status().isOk());

        // Validate the CharacterSkill in the database
        List<CharacterSkill> characterSkills = characterSkillRepository.findAll();
        assertThat(characterSkills).hasSize(1);
        CharacterSkill testCharacterSkill = characterSkills.iterator().next();
        assertThat(testCharacterSkill.getBonus()).isEqualTo(DEFAULT_BONUS);
        assertThat(testCharacterSkill.getPoints()).isEqualTo(DEFAULT_POINTS);
        assertThat(testCharacterSkill.getLevel()).isEqualTo(DEFAULT_LEVEL);
    }

    @Test
    @Transactional
    public void getAllCharacterSkills() throws Exception {
        // Initialize the database
        characterSkillRepository.saveAndFlush(characterSkill);

        // Get all the characterSkills
        restCharacterSkillMockMvc.perform(get("/app/rest/characterSkills"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(characterSkill.getId().intValue()))
                .andExpect(jsonPath("$.[0].bonus").value(DEFAULT_BONUS))
                .andExpect(jsonPath("$.[0].points").value(DEFAULT_POINTS.intValue()))
                .andExpect(jsonPath("$.[0].level").value(DEFAULT_LEVEL));
    }

    @Test
    @Transactional
    public void getCharacterSkill() throws Exception {
        // Initialize the database
        characterSkillRepository.saveAndFlush(characterSkill);

        // Get the characterSkill
        restCharacterSkillMockMvc.perform(get("/app/rest/characterSkills/{id}", characterSkill.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(characterSkill.getId().intValue()))
            .andExpect(jsonPath("$.bonus").value(DEFAULT_BONUS))
            .andExpect(jsonPath("$.points").value(DEFAULT_POINTS.intValue()))
            .andExpect(jsonPath("$.level").value(DEFAULT_LEVEL));
    }

    @Test
    @Transactional
    public void getNonExistingCharacterSkill() throws Exception {
        // Get the characterSkill
        restCharacterSkillMockMvc.perform(get("/app/rest/characterSkills/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCharacterSkill() throws Exception {
        // Initialize the database
        characterSkillRepository.saveAndFlush(characterSkill);

        // Update the characterSkill
        characterSkill.setBonus(UPDATED_BONUS);
        characterSkill.setPoints(UPDATED_POINTS);
        characterSkill.setLevel(UPDATED_LEVEL);
        restCharacterSkillMockMvc.perform(post("/app/rest/characterSkills")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(characterSkill)))
                .andExpect(status().isOk());

        // Validate the CharacterSkill in the database
        List<CharacterSkill> characterSkills = characterSkillRepository.findAll();
        assertThat(characterSkills).hasSize(1);
        CharacterSkill testCharacterSkill = characterSkills.iterator().next();
        assertThat(testCharacterSkill.getBonus()).isEqualTo(UPDATED_BONUS);
        assertThat(testCharacterSkill.getPoints()).isEqualTo(UPDATED_POINTS);
        assertThat(testCharacterSkill.getLevel()).isEqualTo(UPDATED_LEVEL);
    }

    @Test
    @Transactional
    public void deleteCharacterSkill() throws Exception {
        // Initialize the database
        characterSkillRepository.saveAndFlush(characterSkill);

        // Get the characterSkill
        restCharacterSkillMockMvc.perform(delete("/app/rest/characterSkills/{id}", characterSkill.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<CharacterSkill> characterSkills = characterSkillRepository.findAll();
        assertThat(characterSkills).hasSize(0);
    }
}
