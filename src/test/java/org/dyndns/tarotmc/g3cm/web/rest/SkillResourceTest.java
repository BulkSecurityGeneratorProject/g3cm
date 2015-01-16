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
import java.util.List;

import org.dyndns.tarotmc.g3cm.Application;
import org.dyndns.tarotmc.g3cm.domain.Skill;
import org.dyndns.tarotmc.g3cm.repository.SkillRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SkillResource REST controller.
 *
 * @see SkillResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class SkillResourceTest {

    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";
    
    private static final String DEFAULT_DESCRIPTION = "SAMPLE_TEXT";
    private static final String UPDATED_DESCRIPTION = "UPDATED_TEXT";
    
    private static final Integer DEFAULT_DIFFICULTY = 0;
    private static final Integer UPDATED_DIFFICULTY = 1;
    

    @Inject
    private SkillRepository skillRepository;

    private MockMvc restSkillMockMvc;

    private Skill skill;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SkillResource skillResource = new SkillResource();
        ReflectionTestUtils.setField(skillResource, "skillRepository", skillRepository);
        this.restSkillMockMvc = MockMvcBuilders.standaloneSetup(skillResource).build();
    }

    @Before
    public void initTest() {
        skill = new Skill();
        skill.setName(DEFAULT_NAME);
        skill.setDescription(DEFAULT_DESCRIPTION);
        skill.setDifficulty(DEFAULT_DIFFICULTY);
        skillRepository.deleteAll();
    }

    @Test
    @Transactional
    public void createSkill() throws Exception {
        // Validate the database is empty
        assertThat(skillRepository.findAll()).hasSize(0);

        // Create the Skill
        restSkillMockMvc.perform(post("/app/rest/skills")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(skill)))
                .andExpect(status().isOk());

        // Validate the Skill in the database
        List<Skill> skills = skillRepository.findAll();
        assertThat(skills).hasSize(1);
        Skill testSkill = skills.iterator().next();
        assertThat(testSkill.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSkill.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSkill.getDifficulty()).isEqualTo(DEFAULT_DIFFICULTY);
    }

    @Test
    @Transactional
    public void getAllSkills() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skills
        restSkillMockMvc.perform(get("/app/rest/skills"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(skill.getId().intValue()))
                .andExpect(jsonPath("$.[0].name").value(DEFAULT_NAME.toString()))
                .andExpect(jsonPath("$.[0].description").value(DEFAULT_DESCRIPTION.toString()))
                .andExpect(jsonPath("$.[0].difficulty").value(DEFAULT_DIFFICULTY));
    }

    @Test
    @Transactional
    public void getSkill() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get the skill
        restSkillMockMvc.perform(get("/app/rest/skills/{id}", skill.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(skill.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.difficulty").value(DEFAULT_DIFFICULTY));
    }

    @Test
    @Transactional
    public void getNonExistingSkill() throws Exception {
        // Get the skill
        restSkillMockMvc.perform(get("/app/rest/skills/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSkill() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Update the skill
        skill.setName(UPDATED_NAME);
        skill.setDescription(UPDATED_DESCRIPTION);
        skill.setDifficulty(UPDATED_DIFFICULTY);
        restSkillMockMvc.perform(post("/app/rest/skills")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(skill)))
                .andExpect(status().isOk());

        // Validate the Skill in the database
        List<Skill> skills = skillRepository.findAll();
        assertThat(skills).hasSize(1);
        Skill testSkill = skills.iterator().next();
        assertThat(testSkill.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSkill.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSkill.getDifficulty()).isEqualTo(UPDATED_DIFFICULTY);
    }

    @Test
    @Transactional
    public void deleteSkill() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get the skill
        restSkillMockMvc.perform(delete("/app/rest/skills/{id}", skill.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Skill> skills = skillRepository.findAll();
        assertThat(skills).hasSize(0);
    }
}
