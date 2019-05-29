package com.restdemo;

import com.restdemo.model.Student;
import com.restdemo.repository.StudentRepository;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RunApplication.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ControllerStudentTest {

    private MockMvc mockMvc;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setup(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void A_getAllStudentsTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/students").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$",hasSize(9)))
                .andDo(print());
    }

    @Test
    public void B_getStudentByUsernameTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/students/id/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value("Fockin"))
                .andExpect(jsonPath("$.teacher").value("Donald"))
                .andDo(print());
    }

    @Test()
    public void C_getStudentByUsernameExceptionHandlingTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/students/id/11111").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(print());
    }

    @Test
    public void D_getStudentsSearchByNameTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/students/search/Fockin").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andDo(print());
    }

    @Test()
    public void E_getStudentsSearchByNameExceptionHandlingTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/students/search/Fockins").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(print());
    }

    @Test
    public void G_searchStudentByTeacherTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/students/teacher/Jack").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(6)))
                .andDo(print());
    }

    @Test()
    public void H_searchStudentByNameOrUsernameExceptionHandlerTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/students/teacher/Jacks").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(print());
    }

    @Test
    public void I_createStudentTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/students/create")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content("{\n" +
                        "        \"firstName\": \"Tommy\",\n" +
                        "        \"lastName\": \"Lee\",\n" +
                        "        \"email\": \"leeTommy@ukr.net\",\n" +
                        "        \"gender\": \"Male\",\n" +
                        "        \"ipAddress\": \"111.111.111.11\",\n" +
                        "        \"teacher\": \"Donald\"\n" +
                        "    }"))
                .andExpect(jsonPath("$.firstName").value("Tommy"))
                .andExpect(jsonPath("$.lastName").value("Lee"))
                .andExpect(jsonPath("$.email").value("leeTommy@ukr.net"))
                .andExpect(jsonPath("$.teacher").value("Donald"))
                .andDo(print());
    }

    @Test()
    public void J_createStudentExceptionTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/students/create")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content("{\n" +
                        "        \"firstName\": \"Tommy\",\n" +
                        "        \"lastName\": \"Lee\",\n" +
                        "        \"email\": \"leeTommy@ukr.net\",\n" +
                        "        \"gender\": \"Male\",\n" +
                        "        \"ipAddress\": \"111.111.111.11\",\n" +
                        "        \"teacher\": \"Donald\"\n" +
                        "    }"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(print());
    }

    @Test
    public void K_updateStudentTest() throws Exception {
        Student student = studentRepository.findByEmailContaining("leeTommy@ukr.net");
        A.studentID = student.getId();
        mockMvc.perform(MockMvcRequestBuilders.put("/students/update").accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content("{\n" +
                        "    \"id\": " + A.studentID + ",\n" +
                        "    \"firstName\": \"Timmy\",\n" +
                        "    \"lastName\": \"Lee\",\n" +
                        "    \"email\": \"leeTommy@ukr.net\",\n" +
                        "    \"gender\": \"Male\",\n" +
                        "    \"ipAddress\": \"111.111.111.11\",\n" +
                        "    \"teacher\": \"Donald\"\n" +
                        "}"))
                .andExpect(jsonPath("$.firstName").value("Timmy"))
                .andDo(print());
    }

    @Test()
    public void L_updateStudentExceptionHandlingTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/students/update").accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content("{\n" +
                        "    \"id\": 177,\n" +
                        "    \"firstName\": \"Timmy\",\n" +
                        "    \"lastName\": \"Lee\",\n" +
                        "    \"email\": \"leeTommy@ukr.net\",\n" +
                        "    \"gender\": \"Male\",\n" +
                        "    \"ipAddress\": \"111.111.111.11\",\n" +
                        "    \"teacher\": \"Donald\"\n" +
                        "}"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(print());
    }

    @Test
    public void M_deleteStudentTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/students/delete/" + A.studentID).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andDo(print());
    }

    @Test()
    public void N_deleteStudentExceptionHandlingTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/students/delete/" + A.studentID).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(print());
    }
}