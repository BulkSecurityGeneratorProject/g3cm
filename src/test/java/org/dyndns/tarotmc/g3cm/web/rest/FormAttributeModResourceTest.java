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
import org.dyndns.tarotmc.g3cm.domain.FormAttributeMod;
import org.dyndns.tarotmc.g3cm.repository.FormAttributeModRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the FormAttributeModResource REST controller.
 *
 * @see FormAttributeModResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class FormAttributeModResourceTest {

    private static final Integer DEFAULT_MOD = 0;
    private static final Integer UPDATED_MOD = 1;
    

    @Inject
    private FormAttributeModRepository formAttributeModRepository;

    private MockMvc restFormAttributeModMockMvc;

    private FormAttributeMod formAttributeMod;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FormAttributeModResource formAttributeModResource = new FormAttributeModResource();
        ReflectionTestUtils.setField(formAttributeModResource, "formAttributeModRepository", formAttributeModRepository);
        this.restFormAttributeModMockMvc = MockMvcBuilders.standaloneSetup(formAttributeModResource).build();
    }

    @Before
    public void initTest() {
        formAttributeMod = new FormAttributeMod();
        formAttributeMod.setMod(DEFAULT_MOD);
    }

    @Test
    @Transactional
    public void createFormAttributeMod() throws Exception {
        // Validate the database is empty
        assertThat(formAttributeModRepository.findAll()).hasSize(0);

        // Create the FormAttributeMod
        restFormAttributeModMockMvc.perform(post("/app/rest/formAttributeMods")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(formAttributeMod)))
                .andExpect(status().isOk());

        // Validate the FormAttributeMod in the database
        List<FormAttributeMod> formAttributeMods = formAttributeModRepository.findAll();
        assertThat(formAttributeMods).hasSize(1);
        FormAttributeMod testFormAttributeMod = formAttributeMods.iterator().next();
        assertThat(testFormAttributeMod.getMod()).isEqualTo(DEFAULT_MOD);
    }

    @Test
    @Transactional
    public void getAllFormAttributeMods() throws Exception {
        // Initialize the database
        formAttributeModRepository.saveAndFlush(formAttributeMod);

        // Get all the formAttributeMods
        restFormAttributeModMockMvc.perform(get("/app/rest/formAttributeMods"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(formAttributeMod.getId().intValue()))
                .andExpect(jsonPath("$.[0].mod").value(DEFAULT_MOD));
    }

    @Test
    @Transactional
    public void getFormAttributeMod() throws Exception {
        // Initialize the database
        formAttributeModRepository.saveAndFlush(formAttributeMod);

        // Get the formAttributeMod
        restFormAttributeModMockMvc.perform(get("/app/rest/formAttributeMods/{id}", formAttributeMod.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(formAttributeMod.getId().intValue()))
            .andExpect(jsonPath("$.mod").value(DEFAULT_MOD));
    }

    @Test
    @Transactional
    public void getNonExistingFormAttributeMod() throws Exception {
        // Get the formAttributeMod
        restFormAttributeModMockMvc.perform(get("/app/rest/formAttributeMods/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFormAttributeMod() throws Exception {
        // Initialize the database
        formAttributeModRepository.saveAndFlush(formAttributeMod);

        // Update the formAttributeMod
        formAttributeMod.setMod(UPDATED_MOD);
        restFormAttributeModMockMvc.perform(post("/app/rest/formAttributeMods")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(formAttributeMod)))
                .andExpect(status().isOk());

        // Validate the FormAttributeMod in the database
        List<FormAttributeMod> formAttributeMods = formAttributeModRepository.findAll();
        assertThat(formAttributeMods).hasSize(1);
        FormAttributeMod testFormAttributeMod = formAttributeMods.iterator().next();
        assertThat(testFormAttributeMod.getMod()).isEqualTo(UPDATED_MOD);
    }

    @Test
    @Transactional
    public void deleteFormAttributeMod() throws Exception {
        // Initialize the database
        formAttributeModRepository.saveAndFlush(formAttributeMod);

        // Get the formAttributeMod
        restFormAttributeModMockMvc.perform(delete("/app/rest/formAttributeMods/{id}", formAttributeMod.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<FormAttributeMod> formAttributeMods = formAttributeModRepository.findAll();
        assertThat(formAttributeMods).hasSize(0);
    }
}
