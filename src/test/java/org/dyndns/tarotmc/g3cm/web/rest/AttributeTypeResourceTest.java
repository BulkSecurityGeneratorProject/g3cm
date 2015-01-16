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
import org.dyndns.tarotmc.g3cm.domain.AttributeType;
import org.dyndns.tarotmc.g3cm.repository.AttributeTypeRepository;
import org.dyndns.tarotmc.g3cm.repository.SkillRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AttributeTypeResource REST controller.
 *
 * @see AttributeTypeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class AttributeTypeResourceTest {

    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";
    

    @Inject
    private AttributeTypeRepository attributeTypeRepository;
    
    @Inject
    private SkillRepository skillRepository;

    private MockMvc restAttributeTypeMockMvc;

    private AttributeType attributeType;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AttributeTypeResource attributeTypeResource = new AttributeTypeResource();
        ReflectionTestUtils.setField(attributeTypeResource, "attributeTypeRepository", attributeTypeRepository);
        this.restAttributeTypeMockMvc = MockMvcBuilders.standaloneSetup(attributeTypeResource).build();
    }

    @Before
    public void initTest() {
        attributeType = new AttributeType();
        attributeType.setName(DEFAULT_NAME);
        skillRepository.deleteAll();
        attributeTypeRepository.deleteAll();
    }

    @Test
    @Transactional
    public void createAttributeType() throws Exception {
        // Validate the database is empty
        assertThat(attributeTypeRepository.findAll()).hasSize(0);

        // Create the AttributeType
        restAttributeTypeMockMvc.perform(post("/app/rest/attributeTypes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(attributeType)))
                .andExpect(status().isOk());

        // Validate the AttributeType in the database
        List<AttributeType> attributeTypes = attributeTypeRepository.findAll();
        assertThat(attributeTypes).hasSize(1);
        AttributeType testAttributeType = attributeTypes.iterator().next();
        assertThat(testAttributeType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void getAllAttributeTypes() throws Exception {
        // Initialize the database
        attributeTypeRepository.saveAndFlush(attributeType);

        // Get all the attributeTypes
        restAttributeTypeMockMvc.perform(get("/app/rest/attributeTypes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(attributeType.getId().intValue()))
                .andExpect(jsonPath("$.[0].name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getAttributeType() throws Exception {
        // Initialize the database
        attributeTypeRepository.saveAndFlush(attributeType);

        // Get the attributeType
        restAttributeTypeMockMvc.perform(get("/app/rest/attributeTypes/{id}", attributeType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(attributeType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAttributeType() throws Exception {
        // Get the attributeType
        restAttributeTypeMockMvc.perform(get("/app/rest/attributeTypes/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAttributeType() throws Exception {
        // Initialize the database
        attributeTypeRepository.saveAndFlush(attributeType);

        // Update the attributeType
        attributeType.setName(UPDATED_NAME);
        restAttributeTypeMockMvc.perform(post("/app/rest/attributeTypes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(attributeType)))
                .andExpect(status().isOk());

        // Validate the AttributeType in the database
        List<AttributeType> attributeTypes = attributeTypeRepository.findAll();
        assertThat(attributeTypes).hasSize(1);
        AttributeType testAttributeType = attributeTypes.iterator().next();
        assertThat(testAttributeType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void deleteAttributeType() throws Exception {
        // Initialize the database
        attributeTypeRepository.saveAndFlush(attributeType);

        // Get the attributeType
        restAttributeTypeMockMvc.perform(delete("/app/rest/attributeTypes/{id}", attributeType.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<AttributeType> attributeTypes = attributeTypeRepository.findAll();
        assertThat(attributeTypes).hasSize(0);
    }
}
