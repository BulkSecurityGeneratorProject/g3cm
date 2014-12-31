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
import org.dyndns.tarotmc.g3cm.domain.Advantage;
import org.dyndns.tarotmc.g3cm.repository.AdvantageRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AdvantageResource REST controller.
 *
 * @see AdvantageResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class AdvantageResourceTest {

    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";
    
    private static final String DEFAULT_DESCRIPTION = "SAMPLE_TEXT";
    private static final String UPDATED_DESCRIPTION = "UPDATED_TEXT";
    
    private static final Integer DEFAULT_BASE_POINTS = 0;
    private static final Integer UPDATED_BASE_POINTS = 1;
    

    @Inject
    private AdvantageRepository advantageRepository;

    private MockMvc restAdvantageMockMvc;

    private Advantage advantage;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AdvantageResource advantageResource = new AdvantageResource();
        ReflectionTestUtils.setField(advantageResource, "advantageRepository", advantageRepository);
        this.restAdvantageMockMvc = MockMvcBuilders.standaloneSetup(advantageResource).build();
    }

    @Before
    public void initTest() {
        advantage = new Advantage();
        advantage.setName(DEFAULT_NAME);
        advantage.setDescription(DEFAULT_DESCRIPTION);
        advantage.setBasePoints(DEFAULT_BASE_POINTS);
    }

    @Test
    @Transactional
    public void createAdvantage() throws Exception {
        // Validate the database is empty
        assertThat(advantageRepository.findAll()).hasSize(0);

        // Create the Advantage
        restAdvantageMockMvc.perform(post("/app/rest/advantages")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(advantage)))
                .andExpect(status().isOk());

        // Validate the Advantage in the database
        List<Advantage> advantages = advantageRepository.findAll();
        assertThat(advantages).hasSize(1);
        Advantage testAdvantage = advantages.iterator().next();
        assertThat(testAdvantage.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAdvantage.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAdvantage.getBasePoints()).isEqualTo(DEFAULT_BASE_POINTS);
    }

    @Test
    @Transactional
    public void getAllAdvantages() throws Exception {
        // Initialize the database
        advantageRepository.saveAndFlush(advantage);

        // Get all the advantages
        restAdvantageMockMvc.perform(get("/app/rest/advantages"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(advantage.getId().intValue()))
                .andExpect(jsonPath("$.[0].name").value(DEFAULT_NAME.toString()))
                .andExpect(jsonPath("$.[0].description").value(DEFAULT_DESCRIPTION.toString()))
                .andExpect(jsonPath("$.[0].basePoints").value(DEFAULT_BASE_POINTS));
    }

    @Test
    @Transactional
    public void getAdvantage() throws Exception {
        // Initialize the database
        advantageRepository.saveAndFlush(advantage);

        // Get the advantage
        restAdvantageMockMvc.perform(get("/app/rest/advantages/{id}", advantage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(advantage.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.basePoints").value(DEFAULT_BASE_POINTS));
    }

    @Test
    @Transactional
    public void getNonExistingAdvantage() throws Exception {
        // Get the advantage
        restAdvantageMockMvc.perform(get("/app/rest/advantages/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAdvantage() throws Exception {
        // Initialize the database
        advantageRepository.saveAndFlush(advantage);

        // Update the advantage
        advantage.setName(UPDATED_NAME);
        advantage.setDescription(UPDATED_DESCRIPTION);
        advantage.setBasePoints(UPDATED_BASE_POINTS);
        restAdvantageMockMvc.perform(post("/app/rest/advantages")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(advantage)))
                .andExpect(status().isOk());

        // Validate the Advantage in the database
        List<Advantage> advantages = advantageRepository.findAll();
        assertThat(advantages).hasSize(1);
        Advantage testAdvantage = advantages.iterator().next();
        assertThat(testAdvantage.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAdvantage.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAdvantage.getBasePoints()).isEqualTo(UPDATED_BASE_POINTS);
    }

    @Test
    @Transactional
    public void deleteAdvantage() throws Exception {
        // Initialize the database
        advantageRepository.saveAndFlush(advantage);

        // Get the advantage
        restAdvantageMockMvc.perform(delete("/app/rest/advantages/{id}", advantage.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Advantage> advantages = advantageRepository.findAll();
        assertThat(advantages).hasSize(0);
    }
}
