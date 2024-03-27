package com.lucassprakel.alura;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;



@RunWith(SpringRunner.class)
@SpringBootTest
public class RegistrationControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Test
    public void testCreateRegistration() throws Exception {
       
        String registrationJson = """
            {
                "username": "lucas",
                "courseCode": "alura-java"
            }
        """;

        MockMvc mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(registrationJson))
                .andExpect(status().isCreated())
                .andReturn();

        System.out.println("Create Registration Test Result: " + result.getResponse().getContentAsString());
       
    }

    @Test
    public void testGetAllRegistrations() throws Exception {
        MockMvc mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/registrations"))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println("Get All Registrations Test Result: " + result.getResponse().getContentAsString());
    }


    
    @Test
	void contextLoads() {
	}
    

}
