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
import org.dyndns.tarotmc.g3cm.domain.Form;
import org.dyndns.tarotmc.g3cm.repository.FormRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the FormResource REST controller.
 *
 * @see FormResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class FormResourceTest {

    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";
    
    private static final String DEFAULT_INCON = "SAMPLE_TEXT";
    private static final String UPDATED_INCON = "UPDATED_TEXT";
    
    private static final String DEFAULT_PHYSICAL_CHANGE = "SAMPLE_TEXT";
    private static final String UPDATED_PHYSICAL_CHANGE = "UPDATED_TEXT";
    
    private static final Integer DEFAULT_SORT_ORDER = 0;
    private static final Integer UPDATED_SORT_ORDER = 1;
    

    @Inject
    private FormRepository formRepository;

    private MockMvc restFormMockMvc;

    private Form form;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FormResource formResource = new FormResource();
        ReflectionTestUtils.setField(formResource, "formRepository", formRepository);
        this.restFormMockMvc = MockMvcBuilders.standaloneSetup(formResource).build();
    }

    @Before
    public void initTest() {
        form = new Form();
        form.setName(DEFAULT_NAME);
        form.setIncon(DEFAULT_INCON);
        form.setPhysicalChange(DEFAULT_PHYSICAL_CHANGE);
        form.setSortOrder(DEFAULT_SORT_ORDER);
    }

    @Test
    @Transactional
    public void createForm() throws Exception {
        // Validate the database is empty
        assertThat(formRepository.findAll()).hasSize(0);

        // Create the Form
        restFormMockMvc.perform(post("/app/rest/forms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(form)))
                .andExpect(status().isOk());

        // Validate the Form in the database
        List<Form> forms = formRepository.findAll();
        assertThat(forms).hasSize(1);
        Form testForm = forms.iterator().next();
        assertThat(testForm.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testForm.getIncon()).isEqualTo(DEFAULT_INCON);
        assertThat(testForm.getPhysicalChange()).isEqualTo(DEFAULT_PHYSICAL_CHANGE);
        assertThat(testForm.getSortOrder()).isEqualTo(DEFAULT_SORT_ORDER);
    }

    @Test
    @Transactional
    public void getAllForms() throws Exception {
        // Initialize the database
        formRepository.saveAndFlush(form);

        // Get all the forms
        restFormMockMvc.perform(get("/app/rest/forms"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(form.getId().intValue()))
                .andExpect(jsonPath("$.[0].name").value(DEFAULT_NAME.toString()))
                .andExpect(jsonPath("$.[0].incon").value(DEFAULT_INCON.toString()))
                .andExpect(jsonPath("$.[0].physicalChange").value(DEFAULT_PHYSICAL_CHANGE.toString()))
                .andExpect(jsonPath("$.[0].sortOrder").value(DEFAULT_SORT_ORDER));
    }

    @Test
    @Transactional
    public void getForm() throws Exception {
        // Initialize the database
        formRepository.saveAndFlush(form);

        // Get the form
        restFormMockMvc.perform(get("/app/rest/forms/{id}", form.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(form.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.incon").value(DEFAULT_INCON.toString()))
            .andExpect(jsonPath("$.physicalChange").value(DEFAULT_PHYSICAL_CHANGE.toString()))
            .andExpect(jsonPath("$.sortOrder").value(DEFAULT_SORT_ORDER));
    }

    @Test
    @Transactional
    public void getNonExistingForm() throws Exception {
        // Get the form
        restFormMockMvc.perform(get("/app/rest/forms/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateForm() throws Exception {
        // Initialize the database
        formRepository.saveAndFlush(form);

        // Update the form
        form.setName(UPDATED_NAME);
        form.setIncon(UPDATED_INCON);
        form.setPhysicalChange(UPDATED_PHYSICAL_CHANGE);
        form.setSortOrder(UPDATED_SORT_ORDER);
        restFormMockMvc.perform(post("/app/rest/forms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(form)))
                .andExpect(status().isOk());

        // Validate the Form in the database
        List<Form> forms = formRepository.findAll();
        assertThat(forms).hasSize(1);
        Form testForm = forms.iterator().next();
        assertThat(testForm.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testForm.getIncon()).isEqualTo(UPDATED_INCON);
        assertThat(testForm.getPhysicalChange()).isEqualTo(UPDATED_PHYSICAL_CHANGE);
        assertThat(testForm.getSortOrder()).isEqualTo(UPDATED_SORT_ORDER);
    }

    @Test
    @Transactional
    public void deleteForm() throws Exception {
        // Initialize the database
        formRepository.saveAndFlush(form);

        // Get the form
        restFormMockMvc.perform(delete("/app/rest/forms/{id}", form.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Form> forms = formRepository.findAll();
        assertThat(forms).hasSize(0);
    }
}
