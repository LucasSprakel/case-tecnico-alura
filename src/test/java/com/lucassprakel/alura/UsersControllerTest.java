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
public class UsersControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Test
    public void testCreateUser() throws Exception {
        
        String userJson = """
            {
                "name": "Lucas",
                "username": "lucas",
                "email": "lucas@email.com",
                "password": "123",
                "role": "INSTRUCTOR"
            }
        """;

        MockMvc mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isCreated())
                .andReturn();

        System.out.println("Create User Test Result: " + result.getResponse().getContentAsString());
       
    }

    @Test
    public void testGetAllUsers() throws Exception {
        MockMvc mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println("Get All Users Test Result: " + result.getResponse().getContentAsString());
    }

    @Test
    public void testGetUserDetailsByUsername() throws Exception {

        MockMvc mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        
        String username = "lucas"; 
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/user/{username}", username))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println("Get User Details Test Result: " + result.getResponse().getContentAsString());
    }

    
    @Test
	void contextLoads() {
	}
    

}
