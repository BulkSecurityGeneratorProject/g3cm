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
import org.dyndns.tarotmc.g3cm.domain.Character;
import org.dyndns.tarotmc.g3cm.repository.CharacterRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CharacterResource REST controller.
 *
 * @see CharacterResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class CharacterResourceTest {

    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";
    
    private static final Integer DEFAULT_AGE = 0;
    private static final Integer UPDATED_AGE = 1;
    
    private static final String DEFAULT_DESCRIPTION = "SAMPLE_TEXT";
    private static final String UPDATED_DESCRIPTION = "UPDATED_TEXT";
    
    private static final String DEFAULT_BIO = "SAMPLE_TEXT";
    private static final String UPDATED_BIO = "UPDATED_TEXT";
    

    @Inject
    private CharacterRepository characterRepository;

    private MockMvc restCharacterMockMvc;

    private Character character;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CharacterResource characterResource = new CharacterResource();
        ReflectionTestUtils.setField(characterResource, "characterRepository", characterRepository);
        this.restCharacterMockMvc = MockMvcBuilders.standaloneSetup(characterResource).build();
    }

    @Before
    public void initTest() {
        character = new Character();
        character.setName(DEFAULT_NAME);
        character.setAge(DEFAULT_AGE);
        character.setDescription(DEFAULT_DESCRIPTION);
        character.setBio(DEFAULT_BIO);
    }

    @Test
    @Transactional
    public void createCharacter() throws Exception {
        // Validate the database is empty
        assertThat(characterRepository.findAll()).hasSize(0);

        // Create the Character
        restCharacterMockMvc.perform(post("/app/rest/characters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(character)))
                .andExpect(status().isOk());

        // Validate the Character in the database
        List<Character> characters = characterRepository.findAll();
        assertThat(characters).hasSize(1);
        Character testCharacter = characters.iterator().next();
        assertThat(testCharacter.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCharacter.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testCharacter.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCharacter.getBio()).isEqualTo(DEFAULT_BIO);
    }

    @Test
    @Transactional
    public void getAllCharacters() throws Exception {
        // Initialize the database
        characterRepository.saveAndFlush(character);

        // Get all the characters
        restCharacterMockMvc.perform(get("/app/rest/characters"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(character.getId().intValue()))
                .andExpect(jsonPath("$.[0].name").value(DEFAULT_NAME.toString()))
                .andExpect(jsonPath("$.[0].age").value(DEFAULT_AGE))
                .andExpect(jsonPath("$.[0].description").value(DEFAULT_DESCRIPTION.toString()))
                .andExpect(jsonPath("$.[0].bio").value(DEFAULT_BIO.toString()));
    }

    @Test
    @Transactional
    public void getCharacter() throws Exception {
        // Initialize the database
        characterRepository.saveAndFlush(character);

        // Get the character
        restCharacterMockMvc.perform(get("/app/rest/characters/{id}", character.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(character.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.bio").value(DEFAULT_BIO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCharacter() throws Exception {
        // Get the character
        restCharacterMockMvc.perform(get("/app/rest/characters/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCharacter() throws Exception {
        // Initialize the database
        characterRepository.saveAndFlush(character);

        // Update the character
        character.setName(UPDATED_NAME);
        character.setAge(UPDATED_AGE);
        character.setDescription(UPDATED_DESCRIPTION);
        character.setBio(UPDATED_BIO);
        restCharacterMockMvc.perform(post("/app/rest/characters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(character)))
                .andExpect(status().isOk());

        // Validate the Character in the database
        List<Character> characters = characterRepository.findAll();
        assertThat(characters).hasSize(1);
        Character testCharacter = characters.iterator().next();
        assertThat(testCharacter.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCharacter.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testCharacter.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCharacter.getBio()).isEqualTo(UPDATED_BIO);
    }

    @Test
    @Transactional
    public void deleteCharacter() throws Exception {
        // Initialize the database
        characterRepository.saveAndFlush(character);

        // Get the character
        restCharacterMockMvc.perform(delete("/app/rest/characters/{id}", character.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Character> characters = characterRepository.findAll();
        assertThat(characters).hasSize(0);
    }
}
