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
import org.dyndns.tarotmc.g3cm.domain.Pool;
import org.dyndns.tarotmc.g3cm.repository.PoolRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PoolResource REST controller.
 *
 * @see PoolResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class PoolResourceTest {

    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";
    
    private static final String DEFAULT_COLOR = "SAMPLE_TEXT";
    private static final String UPDATED_COLOR = "UPDATED_TEXT";
    

    @Inject
    private PoolRepository poolRepository;

    private MockMvc restPoolMockMvc;

    private Pool pool;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PoolResource poolResource = new PoolResource();
        ReflectionTestUtils.setField(poolResource, "poolRepository", poolRepository);
        this.restPoolMockMvc = MockMvcBuilders.standaloneSetup(poolResource).build();
    }

    @Before
    public void initTest() {
        pool = new Pool();
        pool.setName(DEFAULT_NAME);
        pool.setColor(DEFAULT_COLOR);
    }

    @Test
    @Transactional
    public void createPool() throws Exception {
        // Validate the database is empty
        assertThat(poolRepository.findAll()).hasSize(0);

        // Create the Pool
        restPoolMockMvc.perform(post("/app/rest/pools")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pool)))
                .andExpect(status().isOk());

        // Validate the Pool in the database
        List<Pool> pools = poolRepository.findAll();
        assertThat(pools).hasSize(1);
        Pool testPool = pools.iterator().next();
        assertThat(testPool.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPool.getColor()).isEqualTo(DEFAULT_COLOR);
    }

    @Test
    @Transactional
    public void getAllPools() throws Exception {
        // Initialize the database
        poolRepository.saveAndFlush(pool);

        // Get all the pools
        restPoolMockMvc.perform(get("/app/rest/pools"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(pool.getId().intValue()))
                .andExpect(jsonPath("$.[0].name").value(DEFAULT_NAME.toString()))
                .andExpect(jsonPath("$.[0].color").value(DEFAULT_COLOR.toString()));
    }

    @Test
    @Transactional
    public void getPool() throws Exception {
        // Initialize the database
        poolRepository.saveAndFlush(pool);

        // Get the pool
        restPoolMockMvc.perform(get("/app/rest/pools/{id}", pool.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(pool.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.color").value(DEFAULT_COLOR.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPool() throws Exception {
        // Get the pool
        restPoolMockMvc.perform(get("/app/rest/pools/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePool() throws Exception {
        // Initialize the database
        poolRepository.saveAndFlush(pool);

        // Update the pool
        pool.setName(UPDATED_NAME);
        pool.setColor(UPDATED_COLOR);
        restPoolMockMvc.perform(post("/app/rest/pools")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pool)))
                .andExpect(status().isOk());

        // Validate the Pool in the database
        List<Pool> pools = poolRepository.findAll();
        assertThat(pools).hasSize(1);
        Pool testPool = pools.iterator().next();
        assertThat(testPool.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPool.getColor()).isEqualTo(UPDATED_COLOR);
    }

    @Test
    @Transactional
    public void deletePool() throws Exception {
        // Initialize the database
        poolRepository.saveAndFlush(pool);

        // Get the pool
        restPoolMockMvc.perform(delete("/app/rest/pools/{id}", pool.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Pool> pools = poolRepository.findAll();
        assertThat(pools).hasSize(0);
    }
}
