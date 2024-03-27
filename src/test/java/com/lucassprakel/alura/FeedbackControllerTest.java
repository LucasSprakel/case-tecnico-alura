package com.lucassprakel.alura;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
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


import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@RunWith(SpringRunner.class)
@SpringBootTest
public class FeedbackControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Test
    @Order(1)
    public void testCreateFeedback() throws Exception {
       
        String feedbackJson = """
            {
                "username": "lucas",
                "courseCode": "alura-java",
                "rating": 9,
                "feedback": "OTIMO"
            }
        """;

        MockMvc mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/feedback")
                .contentType(MediaType.APPLICATION_JSON)
                .content(feedbackJson))
                .andExpect(status().isCreated())
                .andReturn();

        System.out.println("Create Feedback Test Result: " + result.getResponse().getContentAsString());
       
    }

    @Test
    @Order(2)
    public void testGetAllFeedbacks() throws Exception {
        MockMvc mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/feedbacks"))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println("Get All Feedbacks Test Result: " + result.getResponse().getContentAsString());
    }

    @Test
    @Order(3)
    public void testEditFeedback() throws Exception {
        
        String feedbackJson = """
            {
                "username": "lucas",
                "courseCode": "alura-java",
                "rating": 7,
                "feedback": "BOM"
            }
        """;

        MockMvc mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        MvcResult result = mvc.perform(MockMvcRequestBuilders.put("/feedback")
                .contentType(MediaType.APPLICATION_JSON)
                .content(feedbackJson))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println("Edit Feedback Test Result: " + result.getResponse().getContentAsString());
       
    }
    
    @Test
    @Order(4)
    public void testGetReport() throws Exception {
        MockMvc mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/report"))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println("Get Feedback NPS report Test Result: " + result.getResponse().getContentAsString());
    }

    @Test
	void contextLoads() {
	}
    

}
