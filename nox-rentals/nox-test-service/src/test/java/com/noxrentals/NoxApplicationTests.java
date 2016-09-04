package com.noxrentals;

import com.noxrentals.domain.Property;
import com.noxrentals.service.PropertyRepository;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.hasSize;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {NoxApplication.class})
@WebAppConfiguration
@DirtiesContext
public class NoxApplicationTests {
    
    private static final int LIST_EXPECTED_COUNT = 5;
    private static final int ADD_EXPECTED_COUNT = 6;
    
    private static final String ADD_PROPERTY_PAYLOAD = 
              "{"
            + "     \"name\": \"Buffets\","
            + "     \"address\": \"63 Victoria Rd\","
            + "     \"suburb\": \"Camps Bay\""
            + "}";

    private static final String UPDATED_OWNER_NAME = "Gecko";    
    private static final String UPDATE_PROPERTY_PAYLOAD = 
              "{"
            + "     \"name\": \"Gecko\","
            + "     \"address\": \"101 Camps Bay Dr\","
            + "     \"suburb\": \"Camps Bay\""
            + "}";

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext webCtx;

    @Autowired
    private PropertyRepository repo;

    @Before
    public void init() {
        mvc = MockMvcBuilders.webAppContextSetup(webCtx).build();
    }

    @Test
    public void successList() throws Exception {
        mvc.perform(get("/api/props"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json;charset=UTF-8"))
                .andExpect(jsonPath("$._embedded.property.*", hasSize(LIST_EXPECTED_COUNT)));
    }

    @Test
    public void successAdd() throws Exception {
        mvc.perform(post("/api/props").contentType(MediaType.APPLICATION_JSON).content(ADD_PROPERTY_PAYLOAD))
                .andExpect(status().isCreated());
                
        assertEquals(ADD_EXPECTED_COUNT, repo.findAll().spliterator().getExactSizeIfKnown());
    }

    @Test
    public void successUpdate() throws Exception {
        MvcResult result = mvc.perform(get("/api/props/search/findByName?name=Jones")).andReturn();
        JSONObject json = new JSONObject(result.getResponse().getContentAsString());

        String uri = json.getJSONObject("_links").getJSONObject("self").getString("href");
        uri = uri.replaceAll("http://localhost", "");
        
        mvc.perform(put(uri).content(UPDATE_PROPERTY_PAYLOAD).contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNoContent());
        
        Property updatedProp = repo.findByName(UPDATED_OWNER_NAME);
        assertNotNull(updatedProp);
        assertEquals(UPDATED_OWNER_NAME, updatedProp.getName());
    }

    @Test
    public void successDelete() throws Exception {
        MvcResult result = mvc.perform(get("/api/props/search/findByName?name=Rothechilds")).andReturn();
        JSONObject json = new JSONObject(result.getResponse().getContentAsString());

        String uri = json.getJSONObject("_links").getJSONObject("self").getString("href");
        uri = uri.replaceAll("http://localhost", "");

        mvc.perform(delete(uri)).andExpect(status().isNoContent());

        mvc.perform(get("/api/props/search/findByName?name=Rothechilds")).andExpect(status().isNotFound());
    }
}
