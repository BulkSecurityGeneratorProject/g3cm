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
import org.dyndns.tarotmc.g3cm.domain.Campaign;
import org.dyndns.tarotmc.g3cm.repository.CampaignRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CampaignResource REST controller.
 *
 * @see CampaignResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class CampaignResourceTest {

    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";
    
    private static final String DEFAULT_DESCRIPTION = "SAMPLE_TEXT";
    private static final String UPDATED_DESCRIPTION = "UPDATED_TEXT";
    
    private static final String DEFAULT_NOTE = "SAMPLE_TEXT";
    private static final String UPDATED_NOTE = "UPDATED_TEXT";
    

    @Inject
    private CampaignRepository campaignRepository;

    private MockMvc restCampaignMockMvc;

    private Campaign campaign;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CampaignResource campaignResource = new CampaignResource();
        ReflectionTestUtils.setField(campaignResource, "campaignRepository", campaignRepository);
        this.restCampaignMockMvc = MockMvcBuilders.standaloneSetup(campaignResource).build();
        campaignRepository.deleteAll();
    }

    @Before
    public void initTest() {
        campaign = new Campaign();
        campaign.setName(DEFAULT_NAME);
        campaign.setDescription(DEFAULT_DESCRIPTION);
        campaign.setNote(DEFAULT_NOTE);
    }

    @Test
    @Transactional
    public void createCampaign() throws Exception {
        // Validate the database is empty
        assertThat(campaignRepository.findAll()).hasSize(0);

        // Create the Campaign
//        restCampaignMockMvc.perform(post("/app/rest/campaigns")
//                .contentType(TestUtil.APPLICATION_JSON_UTF8)
//                .content(TestUtil.convertObjectToJsonBytes(campaign)))
//                .andExpect(status().isOk());

        // Validate the Campaign in the database
        List<Campaign> campaigns = campaignRepository.findAll();
//        assertThat(campaigns).hasSize(1);
//        Campaign testCampaign = campaigns.iterator().next();
//        assertThat(testCampaign.getName()).isEqualTo(DEFAULT_NAME);
//        assertThat(testCampaign.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
//        assertThat(testCampaign.getNote()).isEqualTo(DEFAULT_NOTE);
    }

    @Test
    @Transactional
    public void getAllCampaigns() throws Exception {
        // Initialize the database
        campaignRepository.saveAndFlush(campaign);

        // Get all the campaigns
        restCampaignMockMvc.perform(get("/app/rest/campaigns"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(campaign.getId().intValue()))
                .andExpect(jsonPath("$.[0].name").value(DEFAULT_NAME.toString()))
                .andExpect(jsonPath("$.[0].description").value(DEFAULT_DESCRIPTION.toString()))
                .andExpect(jsonPath("$.[0].note").value(DEFAULT_NOTE.toString()));
    }

    @Test
    @Transactional
    public void getCampaign() throws Exception {
        // Initialize the database
        campaignRepository.saveAndFlush(campaign);

        // Get the campaign
        restCampaignMockMvc.perform(get("/app/rest/campaigns/{id}", campaign.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(campaign.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCampaign() throws Exception {
        // Get the campaign
        restCampaignMockMvc.perform(get("/app/rest/campaigns/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCampaign() throws Exception {
        // Initialize the database
        campaignRepository.saveAndFlush(campaign);

        // Update the campaign
        campaign.setName(UPDATED_NAME);
        campaign.setDescription(UPDATED_DESCRIPTION);
        campaign.setNote(UPDATED_NOTE);
//        restCampaignMockMvc.perform(post("/app/rest/campaigns")
//                .contentType(TestUtil.APPLICATION_JSON_UTF8)
//                .content(TestUtil.convertObjectToJsonBytes(campaign)))
//                .andExpect(status().isOk());

        // Validate the Campaign in the database
        List<Campaign> campaigns = campaignRepository.findAll();
        assertThat(campaigns).hasSize(1);
        Campaign testCampaign = campaigns.iterator().next();
        assertThat(testCampaign.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCampaign.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCampaign.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void deleteCampaign() throws Exception {
        // Initialize the database
        campaignRepository.saveAndFlush(campaign);

        // Get the campaign
        restCampaignMockMvc.perform(delete("/app/rest/campaigns/{id}", campaign.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Campaign> campaigns = campaignRepository.findAll();
        assertThat(campaigns).hasSize(0);
    }
}
