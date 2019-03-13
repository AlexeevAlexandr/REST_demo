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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RunApplication.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ControllerStudentTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setup(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void A_getAllStudentsTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/student").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$",hasSize(4)))
                .andDo(print());
    }

    @Test
    public void B_getStudentByUsernameTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/student/peter").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value("peter"))
                .andExpect(jsonPath("$.name").value("Peter"))
                .andExpect(jsonPath("$.teacher").value("Jack"))
                .andDo(print());
    }

    @Test(expected = NestedServletException.class)
    public void C_getStudentByUsernameExceptionHandlingTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/student/peters").accept(MediaType.APPLICATION_JSON))
                .andDo(print());
    }


    @Test
    public void D_getStudentsByTeacherTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/student/searchByTeacher").contentType(MediaType.APPLICATION_JSON)
                .param("teacher","Jack"))
                .andExpect(jsonPath("$", hasSize(1)))
                .andDo(print());
    }

    @Test(expected = NestedServletException.class)
    public void E_getStudentsByTeacherExceptionHandlerTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/student/searchByTeacher").contentType(MediaType.APPLICATION_JSON)
                .param("teacher","Jackson"))
                .andDo(print());
    }

    @Test
    public void F_getStudentsByTeacherTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/student/searchByTeacher").contentType(MediaType.APPLICATION_JSON)
                .param("teacher","Daniels"))
                .andExpect(jsonPath("$", hasSize(3)))
                .andDo(print());
    }

    @Test
    public void G_searchStudentByNameOrUsernameTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/student/search").accept(MediaType.APPLICATION_JSON)
                .param("text", "ann"))
                .andExpect(content().json("[{'username':'ann','name':'Anna','teacher':'Daniels'}]"))
                .andDo(print());
    }

    @Test(expected = NestedServletException.class)
    public void H_searchStudentByNameOrUsernameExceptionHandlerTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/student/search").accept(MediaType.APPLICATION_JSON)
                .param("text", "tony"))
                .andDo(print());
    }

    @Test
    public void I_createStudentTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/student").accept(MediaType.APPLICATION_JSON)
                .param("username", "linda")
                .param("name", "Linda")
                .param("teacher", "Jack"))
                .andExpect(jsonPath("$.username").value("linda"))
                .andExpect(jsonPath("$.name").value("Linda"))
                .andExpect(jsonPath("$.teacher").value("Jack"))
                .andDo(print());
    }

    @Test(expected = NestedServletException.class)
    public void J_createStudentExceptionTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/student").accept(MediaType.APPLICATION_JSON)
                .param("username", "linda")
                .param("name", "Linda")
                .param("teacher", "Jack"))
                .andDo(print());
    }


    @Test
    public void K_updateStudentTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/student/linda").accept(MediaType.APPLICATION_JSON)
                .param("name", "Lindi")
                .param("teacher", "Daniels"))
                .andExpect(jsonPath("$.username").value("linda"))
                .andExpect(jsonPath("$.name").value("Lindi"))
                .andExpect(jsonPath("$.teacher").value("Daniels"))
                .andDo(print());
    }

    @Test(expected = NestedServletException.class)
    public void L_updateStudentExceptionHandlingTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/student/villi").accept(MediaType.APPLICATION_JSON)
                .param("name", "Villi")
                .param("teacher", "Tomas"))
                .andDo(print());
    }

    @Test
    public void M_deleteStudentTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/student/linda").accept(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @Test(expected = NestedServletException.class)
    public void N_deleteStudentExceptionHandlingTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/student/lindy").accept(MediaType.APPLICATION_JSON))
                .andDo(print());
    }
}
