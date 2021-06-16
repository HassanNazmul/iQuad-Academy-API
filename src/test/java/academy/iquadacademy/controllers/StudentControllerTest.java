package academy.iquadacademy.controllers;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;

import academy.iquadacademy.domain.Result;
import academy.iquadacademy.domain.StudentService;
import academy.iquadacademy.models.Student;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {StudentController.class})
@ExtendWith(SpringExtension.class)
public class StudentControllerTest {
    @Autowired
    private StudentController studentController;

    @MockBean
    private StudentService studentService;

    @Test
    public void testFindAll() throws Exception {
        // Arrange
        when(this.studentService.findAll()).thenReturn(new ArrayList<Student>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/student");
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.studentController).build();

        // Act
        ResultActions actualPerformResult = buildResult.perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    public void testFindAll2() throws Exception {
        // Arrange
        when(this.studentService.findAll()).thenReturn(new ArrayList<Student>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/student");
        getResult.contentType("Not all who wander are lost");
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.studentController).build();

        // Act
        ResultActions actualPerformResult = buildResult.perform(getResult);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    public void testFindById() throws Exception {
        // Arrange
        when(this.studentService.findById(anyInt())).thenReturn(new Student());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/student/{studentID}", 1);
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.studentController).build();

        // Act
        ResultActions actualPerformResult = buildResult.perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"studentID\":0,\"firstName\":null,\"lastName\":null,\"dob\":null,\"depID\":0}"));
    }

    @Test
    public void testUpdate() throws Exception {
        // Arrange
        when(this.studentService.update((Student) any())).thenReturn(new Result<Student>());

        Student student = new Student();
        student.setLastName("Doe");
        student.setDepID(1);
        student.setDob(null);
        student.setStudentID(1);
        student.setFirstName("Jane");
        String content = (new ObjectMapper()).writeValueAsString(student);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/student/{studentID}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.studentController).build();

        // Act
        ResultActions actualPerformResult = buildResult.perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testUpdate2() throws Exception {
        // Arrange
        when(this.studentService.update((Student) any())).thenReturn(new Result<Student>());

        Student student = new Student();
        student.setLastName("Doe");
        student.setDepID(1);
        student.setDob(null);
        student.setStudentID(0);
        student.setFirstName("Jane");
        String content = (new ObjectMapper()).writeValueAsString(student);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/student/{studentID}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.studentController).build();

        // Act
        ResultActions actualPerformResult = buildResult.perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(409));
    }

    @Test
    public void testAdd() throws Exception {
        // Arrange
        when(this.studentService.findAll()).thenReturn(new ArrayList<Student>());

        Student student = new Student();
        student.setLastName("Doe");
        student.setDepID(1);
        student.setDob(null);
        student.setStudentID(1);
        student.setFirstName("Jane");
        String content = (new ObjectMapper()).writeValueAsString(student);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/student")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.studentController).build();

        // Act
        ResultActions actualPerformResult = buildResult.perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    public void testDeleteById() throws Exception {
        // Arrange
        when(this.studentService.deleteById(anyInt())).thenReturn(true);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/student/{studentID}", 1);
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.studentController).build();

        // Act
        ResultActions actualPerformResult = buildResult.perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testDeleteById2() throws Exception {
        // Arrange
        when(this.studentService.deleteById(anyInt())).thenReturn(false);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/student/{studentID}", 1);
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.studentController).build();

        // Act
        ResultActions actualPerformResult = buildResult.perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testDeleteById3() throws Exception {
        // Arrange
        when(this.studentService.deleteById(anyInt())).thenReturn(true);
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/student/{studentID}", 1);
        deleteResult.contentType("Not all who wander are lost");
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.studentController).build();

        // Act
        ResultActions actualPerformResult = buildResult.perform(deleteResult);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}

