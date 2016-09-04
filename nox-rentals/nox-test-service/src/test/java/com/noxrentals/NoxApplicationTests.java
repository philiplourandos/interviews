package com.noxrentals;

import com.noxrentals.service.PropertyRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest
@DataJpaTest
public class NoxApplicationTests {
    
    private static final int LIST_EXPECTED_COUNT = 5;
    private static final int ADD_EXPECTED_COUNT = 6;
    
    private static final String ADD_PROPERTY_PAYLOAD = 
              "{"
            + "     \"name\": \"Buffets\","
            + "     \"address\": \"63 Victoria Rd\""
            + "     \"suburb\": \"Camps Bay\""
            + "}";
    
    private static final String UPDATE_PROPERTY_PAYLOAD = 
              "{"
            + "     \"id\": \"$ID\","
            + "     \"name\": \"Gecko\","
            + "     \"address\": \"63 Victoria Rd\""
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
        mvc.perform(get("/api/property"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(LIST_EXPECTED_COUNT)));
    }

    @Test
    public void add() throws Exception {
        mvc.perform(post("/api/property").contentType(MediaType.APPLICATION_JSON).content(ADD_PROPERTY_PAYLOAD))
                .andExpect(status().isCreated());
                
        assertEquals(ADD_EXPECTED_COUNT, repo.findAll().spliterator().getExactSizeIfKnown());
    }

    @Test
    public void update() throws Exception {
        fail();
    }

    @Test
    public void delete() throws Exception {
        fail();
    }
}
