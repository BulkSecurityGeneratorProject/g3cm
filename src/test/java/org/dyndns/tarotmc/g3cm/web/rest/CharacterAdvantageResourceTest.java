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
import org.dyndns.tarotmc.g3cm.domain.CharacterAdvantage;
import org.dyndns.tarotmc.g3cm.repository.CharacterAdvantageRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CharacterAdvantageResource REST controller.
 *
 * @see CharacterAdvantageResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class CharacterAdvantageResourceTest {

    private static final Integer DEFAULT_POINTS = 0;
    private static final Integer UPDATED_POINTS = 1;
    

    @Inject
    private CharacterAdvantageRepository characterAdvantageRepository;

    private MockMvc restCharacterAdvantageMockMvc;

    private CharacterAdvantage characterAdvantage;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CharacterAdvantageResource characterAdvantageResource = new CharacterAdvantageResource();
        ReflectionTestUtils.setField(characterAdvantageResource, "characterAdvantageRepository", characterAdvantageRepository);
        this.restCharacterAdvantageMockMvc = MockMvcBuilders.standaloneSetup(characterAdvantageResource).build();
    }

    @Before
    public void initTest() {
        characterAdvantage = new CharacterAdvantage();
        characterAdvantage.setPoints(DEFAULT_POINTS);
    }

    @Test
    @Transactional
    public void createCharacterAdvantage() throws Exception {
        // Validate the database is empty
        assertThat(characterAdvantageRepository.findAll()).hasSize(0);

        // Create the CharacterAdvantage
        restCharacterAdvantageMockMvc.perform(post("/app/rest/characterAdvantages")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(characterAdvantage)))
                .andExpect(status().isOk());

        // Validate the CharacterAdvantage in the database
        List<CharacterAdvantage> characterAdvantages = characterAdvantageRepository.findAll();
        assertThat(characterAdvantages).hasSize(1);
        CharacterAdvantage testCharacterAdvantage = characterAdvantages.iterator().next();
        assertThat(testCharacterAdvantage.getPoints()).isEqualTo(DEFAULT_POINTS);
    }

    @Test
    @Transactional
    public void getAllCharacterAdvantages() throws Exception {
        // Initialize the database
        characterAdvantageRepository.saveAndFlush(characterAdvantage);

        // Get all the characterAdvantages
        restCharacterAdvantageMockMvc.perform(get("/app/rest/characterAdvantages"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(characterAdvantage.getId().intValue()))
                .andExpect(jsonPath("$.[0].points").value(DEFAULT_POINTS));
    }

    @Test
    @Transactional
    public void getCharacterAdvantage() throws Exception {
        // Initialize the database
        characterAdvantageRepository.saveAndFlush(characterAdvantage);

        // Get the characterAdvantage
        restCharacterAdvantageMockMvc.perform(get("/app/rest/characterAdvantages/{id}", characterAdvantage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(characterAdvantage.getId().intValue()))
            .andExpect(jsonPath("$.points").value(DEFAULT_POINTS));
    }

    @Test
    @Transactional
    public void getNonExistingCharacterAdvantage() throws Exception {
        // Get the characterAdvantage
        restCharacterAdvantageMockMvc.perform(get("/app/rest/characterAdvantages/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCharacterAdvantage() throws Exception {
        // Initialize the database
        characterAdvantageRepository.saveAndFlush(characterAdvantage);

        // Update the characterAdvantage
        characterAdvantage.setPoints(UPDATED_POINTS);
        restCharacterAdvantageMockMvc.perform(post("/app/rest/characterAdvantages")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(characterAdvantage)))
                .andExpect(status().isOk());

        // Validate the CharacterAdvantage in the database
        List<CharacterAdvantage> characterAdvantages = characterAdvantageRepository.findAll();
        assertThat(characterAdvantages).hasSize(1);
        CharacterAdvantage testCharacterAdvantage = characterAdvantages.iterator().next();
        assertThat(testCharacterAdvantage.getPoints()).isEqualTo(UPDATED_POINTS);
    }

    @Test
    @Transactional
    public void deleteCharacterAdvantage() throws Exception {
        // Initialize the database
        characterAdvantageRepository.saveAndFlush(characterAdvantage);

        // Get the characterAdvantage
        restCharacterAdvantageMockMvc.perform(delete("/app/rest/characterAdvantages/{id}", characterAdvantage.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<CharacterAdvantage> characterAdvantages = characterAdvantageRepository.findAll();
        assertThat(characterAdvantages).hasSize(0);
    }
}
