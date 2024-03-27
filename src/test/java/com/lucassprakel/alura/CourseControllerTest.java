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
public class CourseControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Test
    public void testCreateCourse() throws Exception {
        
        String courseJson = """
            {
                "name": "Java",
                "code": "alura-java",
                "instructor": "lucas",
                "description": "Curso de Java"
            }
        """;

        MockMvc mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/course")
                .contentType(MediaType.APPLICATION_JSON)
                .content(courseJson))
                .andExpect(status().isCreated())
                .andReturn();

        System.out.println("Create Course Test Result: " + result.getResponse().getContentAsString());
       
    }

    @Test
    public void testGetCourses() throws Exception {
        MockMvc mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        
        MvcResult resultWithoutParameters = mvc.perform(MockMvcRequestBuilders.get("/courses"))
            .andExpect(status().isOk())
            .andReturn();

        MvcResult resultWithParameters = mvc.perform(MockMvcRequestBuilders.get("/courses")
            .param("status", "Active")
            .param("page", "0")
            .param("size", "1"))
            .andExpect(status().isOk())
            .andReturn();

        System.out.println("Get Courses Test Result (without parameters): " + resultWithoutParameters.getResponse().getContentAsString());
        System.out.println("Get Courses Test Result (with parameters): " + resultWithParameters.getResponse().getContentAsString());
    }


    @Test
    public void testEditCourse() throws Exception {

        MockMvc mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        
        String code = "alura-jav"; 
        MvcResult result = mvc.perform(MockMvcRequestBuilders.put("/course/{code}/disable", code))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println("Disable Course Test Result: " + result.getResponse().getContentAsString());
       
    }

    
    @Test
	void contextLoads() {
	}
    

}
