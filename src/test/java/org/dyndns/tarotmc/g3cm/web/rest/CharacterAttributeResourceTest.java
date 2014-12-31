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
import org.dyndns.tarotmc.g3cm.domain.CharacterAttribute;
import org.dyndns.tarotmc.g3cm.repository.CharacterAttributeRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CharacterAttributeResource REST controller.
 *
 * @see CharacterAttributeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class CharacterAttributeResourceTest {

    private static final Integer DEFAULT_RATING = 0;
    private static final Integer UPDATED_RATING = 1;
    
    private static final Integer DEFAULT_BONUS = 0;
    private static final Integer UPDATED_BONUS = 1;
    
    private static final Integer DEFAULT_POINTS = 0;
    private static final Integer UPDATED_POINTS = 1;
    

    @Inject
    private CharacterAttributeRepository characterAttributeRepository;

    private MockMvc restCharacterAttributeMockMvc;

    private CharacterAttribute characterAttribute;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CharacterAttributeResource characterAttributeResource = new CharacterAttributeResource();
        ReflectionTestUtils.setField(characterAttributeResource, "characterAttributeRepository", characterAttributeRepository);
        this.restCharacterAttributeMockMvc = MockMvcBuilders.standaloneSetup(characterAttributeResource).build();
    }

    @Before
    public void initTest() {
        characterAttribute = new CharacterAttribute();
        characterAttribute.setRating(DEFAULT_RATING);
        characterAttribute.setBonus(DEFAULT_BONUS);
        characterAttribute.setPoints(DEFAULT_POINTS);
    }

    @Test
    @Transactional
    public void createCharacterAttribute() throws Exception {
        // Validate the database is empty
        assertThat(characterAttributeRepository.findAll()).hasSize(0);

        // Create the CharacterAttribute
        restCharacterAttributeMockMvc.perform(post("/app/rest/characterAttributes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(characterAttribute)))
                .andExpect(status().isOk());

        // Validate the CharacterAttribute in the database
        List<CharacterAttribute> characterAttributes = characterAttributeRepository.findAll();
        assertThat(characterAttributes).hasSize(1);
        CharacterAttribute testCharacterAttribute = characterAttributes.iterator().next();
        assertThat(testCharacterAttribute.getRating()).isEqualTo(DEFAULT_RATING);
        assertThat(testCharacterAttribute.getBonus()).isEqualTo(DEFAULT_BONUS);
        assertThat(testCharacterAttribute.getPoints()).isEqualTo(DEFAULT_POINTS);
    }

    @Test
    @Transactional
    public void getAllCharacterAttributes() throws Exception {
        // Initialize the database
        characterAttributeRepository.saveAndFlush(characterAttribute);

        // Get all the characterAttributes
        restCharacterAttributeMockMvc.perform(get("/app/rest/characterAttributes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(characterAttribute.getId().intValue()))
                .andExpect(jsonPath("$.[0].rating").value(DEFAULT_RATING))
                .andExpect(jsonPath("$.[0].bonus").value(DEFAULT_BONUS))
                .andExpect(jsonPath("$.[0].points").value(DEFAULT_POINTS));
    }

    @Test
    @Transactional
    public void getCharacterAttribute() throws Exception {
        // Initialize the database
        characterAttributeRepository.saveAndFlush(characterAttribute);

        // Get the characterAttribute
        restCharacterAttributeMockMvc.perform(get("/app/rest/characterAttributes/{id}", characterAttribute.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(characterAttribute.getId().intValue()))
            .andExpect(jsonPath("$.rating").value(DEFAULT_RATING))
            .andExpect(jsonPath("$.bonus").value(DEFAULT_BONUS))
            .andExpect(jsonPath("$.points").value(DEFAULT_POINTS));
    }

    @Test
    @Transactional
    public void getNonExistingCharacterAttribute() throws Exception {
        // Get the characterAttribute
        restCharacterAttributeMockMvc.perform(get("/app/rest/characterAttributes/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCharacterAttribute() throws Exception {
        // Initialize the database
        characterAttributeRepository.saveAndFlush(characterAttribute);

        // Update the characterAttribute
        characterAttribute.setRating(UPDATED_RATING);
        characterAttribute.setBonus(UPDATED_BONUS);
        characterAttribute.setPoints(UPDATED_POINTS);
        restCharacterAttributeMockMvc.perform(post("/app/rest/characterAttributes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(characterAttribute)))
                .andExpect(status().isOk());

        // Validate the CharacterAttribute in the database
        List<CharacterAttribute> characterAttributes = characterAttributeRepository.findAll();
        assertThat(characterAttributes).hasSize(1);
        CharacterAttribute testCharacterAttribute = characterAttributes.iterator().next();
        assertThat(testCharacterAttribute.getRating()).isEqualTo(UPDATED_RATING);
        assertThat(testCharacterAttribute.getBonus()).isEqualTo(UPDATED_BONUS);
        assertThat(testCharacterAttribute.getPoints()).isEqualTo(UPDATED_POINTS);
    }

    @Test
    @Transactional
    public void deleteCharacterAttribute() throws Exception {
        // Initialize the database
        characterAttributeRepository.saveAndFlush(characterAttribute);

        // Get the characterAttribute
        restCharacterAttributeMockMvc.perform(delete("/app/rest/characterAttributes/{id}", characterAttribute.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<CharacterAttribute> characterAttributes = characterAttributeRepository.findAll();
        assertThat(characterAttributes).hasSize(0);
    }
}
