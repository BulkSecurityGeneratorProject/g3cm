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
import org.dyndns.tarotmc.g3cm.domain.CharacterPool;
import org.dyndns.tarotmc.g3cm.repository.CharacterPoolRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CharacterPoolResource REST controller.
 *
 * @see CharacterPoolResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class CharacterPoolResourceTest {

    private static final Long DEFAULT_MAX = 0L;
    private static final Long UPDATED_MAX = 1L;
    
    private static final Long DEFAULT_CURRENT = 0L;
    private static final Long UPDATED_CURRENT = 1L;
    

    @Inject
    private CharacterPoolRepository characterPoolRepository;

    private MockMvc restCharacterPoolMockMvc;

    private CharacterPool characterPool;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CharacterPoolResource characterPoolResource = new CharacterPoolResource();
        ReflectionTestUtils.setField(characterPoolResource, "characterPoolRepository", characterPoolRepository);
        this.restCharacterPoolMockMvc = MockMvcBuilders.standaloneSetup(characterPoolResource).build();
    }

    @Before
    public void initTest() {
        characterPool = new CharacterPool();
        characterPool.setMax(DEFAULT_MAX);
        characterPool.setCurrent(DEFAULT_CURRENT);
    }

    @Test
    @Transactional
    public void createCharacterPool() throws Exception {
        // Validate the database is empty
        assertThat(characterPoolRepository.findAll()).hasSize(0);

        // Create the CharacterPool
        restCharacterPoolMockMvc.perform(post("/app/rest/characterPools")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(characterPool)))
                .andExpect(status().isOk());

        // Validate the CharacterPool in the database
        List<CharacterPool> characterPools = characterPoolRepository.findAll();
        assertThat(characterPools).hasSize(1);
        CharacterPool testCharacterPool = characterPools.iterator().next();
        assertThat(testCharacterPool.getMax()).isEqualTo(DEFAULT_MAX);
        assertThat(testCharacterPool.getCurrent()).isEqualTo(DEFAULT_CURRENT);
    }

    @Test
    @Transactional
    public void getAllCharacterPools() throws Exception {
        // Initialize the database
        characterPoolRepository.saveAndFlush(characterPool);

        // Get all the characterPools
        restCharacterPoolMockMvc.perform(get("/app/rest/characterPools"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(characterPool.getId().intValue()))
                .andExpect(jsonPath("$.[0].max").value(DEFAULT_MAX.intValue()))
                .andExpect(jsonPath("$.[0].current").value(DEFAULT_CURRENT.intValue()));
    }

    @Test
    @Transactional
    public void getCharacterPool() throws Exception {
        // Initialize the database
        characterPoolRepository.saveAndFlush(characterPool);

        // Get the characterPool
        restCharacterPoolMockMvc.perform(get("/app/rest/characterPools/{id}", characterPool.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(characterPool.getId().intValue()))
            .andExpect(jsonPath("$.max").value(DEFAULT_MAX.intValue()))
            .andExpect(jsonPath("$.current").value(DEFAULT_CURRENT.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCharacterPool() throws Exception {
        // Get the characterPool
        restCharacterPoolMockMvc.perform(get("/app/rest/characterPools/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCharacterPool() throws Exception {
        // Initialize the database
        characterPoolRepository.saveAndFlush(characterPool);

        // Update the characterPool
        characterPool.setMax(UPDATED_MAX);
        characterPool.setCurrent(UPDATED_CURRENT);
        restCharacterPoolMockMvc.perform(post("/app/rest/characterPools")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(characterPool)))
                .andExpect(status().isOk());

        // Validate the CharacterPool in the database
        List<CharacterPool> characterPools = characterPoolRepository.findAll();
        assertThat(characterPools).hasSize(1);
        CharacterPool testCharacterPool = characterPools.iterator().next();
        assertThat(testCharacterPool.getMax()).isEqualTo(UPDATED_MAX);
        assertThat(testCharacterPool.getCurrent()).isEqualTo(UPDATED_CURRENT);
    }

    @Test
    @Transactional
    public void deleteCharacterPool() throws Exception {
        // Initialize the database
        characterPoolRepository.saveAndFlush(characterPool);

        // Get the characterPool
        restCharacterPoolMockMvc.perform(delete("/app/rest/characterPools/{id}", characterPool.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<CharacterPool> characterPools = characterPoolRepository.findAll();
        assertThat(characterPools).hasSize(0);
    }
}
