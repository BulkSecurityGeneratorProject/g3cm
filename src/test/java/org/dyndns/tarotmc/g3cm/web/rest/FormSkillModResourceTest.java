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
import org.dyndns.tarotmc.g3cm.domain.FormSkillMod;
import org.dyndns.tarotmc.g3cm.repository.FormSkillModRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the FormSkillModResource REST controller.
 *
 * @see FormSkillModResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class FormSkillModResourceTest {

    private static final Integer DEFAULT_LEVEL_CHANGE = 0;
    private static final Integer UPDATED_LEVEL_CHANGE = 1;
    

    @Inject
    private FormSkillModRepository formSkillModRepository;

    private MockMvc restFormSkillModMockMvc;

    private FormSkillMod formSkillMod;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FormSkillModResource formSkillModResource = new FormSkillModResource();
        ReflectionTestUtils.setField(formSkillModResource, "formSkillModRepository", formSkillModRepository);
        this.restFormSkillModMockMvc = MockMvcBuilders.standaloneSetup(formSkillModResource).build();
    }

    @Before
    public void initTest() {
        formSkillMod = new FormSkillMod();
        formSkillMod.setLevelChange(DEFAULT_LEVEL_CHANGE);
    }

    @Test
    @Transactional
    public void createFormSkillMod() throws Exception {
        // Validate the database is empty
        assertThat(formSkillModRepository.findAll()).hasSize(0);

        // Create the FormSkillMod
        restFormSkillModMockMvc.perform(post("/app/rest/formSkillMods")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(formSkillMod)))
                .andExpect(status().isOk());

        // Validate the FormSkillMod in the database
        List<FormSkillMod> formSkillMods = formSkillModRepository.findAll();
        assertThat(formSkillMods).hasSize(1);
        FormSkillMod testFormSkillMod = formSkillMods.iterator().next();
        assertThat(testFormSkillMod.getLevelChange()).isEqualTo(DEFAULT_LEVEL_CHANGE);
    }

    @Test
    @Transactional
    public void getAllFormSkillMods() throws Exception {
        // Initialize the database
        formSkillModRepository.saveAndFlush(formSkillMod);

        // Get all the formSkillMods
        restFormSkillModMockMvc.perform(get("/app/rest/formSkillMods"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(formSkillMod.getId().intValue()))
                .andExpect(jsonPath("$.[0].levelChange").value(DEFAULT_LEVEL_CHANGE));
    }

    @Test
    @Transactional
    public void getFormSkillMod() throws Exception {
        // Initialize the database
        formSkillModRepository.saveAndFlush(formSkillMod);

        // Get the formSkillMod
        restFormSkillModMockMvc.perform(get("/app/rest/formSkillMods/{id}", formSkillMod.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(formSkillMod.getId().intValue()))
            .andExpect(jsonPath("$.levelChange").value(DEFAULT_LEVEL_CHANGE));
    }

    @Test
    @Transactional
    public void getNonExistingFormSkillMod() throws Exception {
        // Get the formSkillMod
        restFormSkillModMockMvc.perform(get("/app/rest/formSkillMods/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFormSkillMod() throws Exception {
        // Initialize the database
        formSkillModRepository.saveAndFlush(formSkillMod);

        // Update the formSkillMod
        formSkillMod.setLevelChange(UPDATED_LEVEL_CHANGE);
        restFormSkillModMockMvc.perform(post("/app/rest/formSkillMods")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(formSkillMod)))
                .andExpect(status().isOk());

        // Validate the FormSkillMod in the database
        List<FormSkillMod> formSkillMods = formSkillModRepository.findAll();
        assertThat(formSkillMods).hasSize(1);
        FormSkillMod testFormSkillMod = formSkillMods.iterator().next();
        assertThat(testFormSkillMod.getLevelChange()).isEqualTo(UPDATED_LEVEL_CHANGE);
    }

    @Test
    @Transactional
    public void deleteFormSkillMod() throws Exception {
        // Initialize the database
        formSkillModRepository.saveAndFlush(formSkillMod);

        // Get the formSkillMod
        restFormSkillModMockMvc.perform(delete("/app/rest/formSkillMods/{id}", formSkillMod.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<FormSkillMod> formSkillMods = formSkillModRepository.findAll();
        assertThat(formSkillMods).hasSize(0);
    }
}
