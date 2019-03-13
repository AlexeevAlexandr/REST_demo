package com.restdemo;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.NestedServletException;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RunApplication.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ControllerTeacherTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setup(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void A_getAllTeachersTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/teacher").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$",hasSize(4)))
                .andDo(print());
    }

    @Test
    public void B_getTeacherByNameTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/teacher/Jack").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Jack"))
                .andExpect(jsonPath("$.password").value("123"))
                .andDo(print());
    }

    @Test(expected = NestedServletException.class)
    public void C_getTeacherByNameExceptionHandlerTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/teacher/Jackson").accept(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @Test
    public void D_createTeacherTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/teacher").contentType(MediaType.APPLICATION_JSON)
                .param("name","Anton").param("password", "abc123"))
                .andExpect(jsonPath("$.name").value("Anton"))
                .andExpect(jsonPath("$.password").value("abc123"))
                .andDo(print());
    }

    @Test(expected = NestedServletException.class)
    public void E_createTeacherExceptionHandlingTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/teacher").contentType(MediaType.APPLICATION_JSON)
                .param("name","Anton").param("password", "abc123"))
                .andDo(print());
    }

    @Test
    public void F_updateTeacherTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/teacher/Anton").accept(MediaType.APPLICATION_JSON)
                .param("password", "123abc"))
                .andExpect(jsonPath("$.name").value("Anton"))
                .andExpect(jsonPath("password").value("123abc"))
                .andDo(print());
    }

    @Test(expected = NestedServletException.class)
    public void G_updateTeacherExceptionHandlingTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/teacher/Antonio").accept(MediaType.APPLICATION_JSON)
                .param("password", "123abc"))
                .andDo(print());
    }

    @Test
    public void H_deleteTeacherTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/teacher/Anton").accept(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @Test(expected = NestedServletException.class)
    public void H_deleteTeacherExceptionHandlingTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/teacher/Antonio").accept(MediaType.APPLICATION_JSON))
                .andDo(print());
    }
}
