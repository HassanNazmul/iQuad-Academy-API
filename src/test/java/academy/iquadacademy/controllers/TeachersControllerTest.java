package academy.iquadacademy.controllers;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;

import academy.iquadacademy.domain.Result;
import academy.iquadacademy.domain.TeachersService;
import academy.iquadacademy.models.Teachers;
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

@ContextConfiguration(classes = {TeachersController.class})
@ExtendWith(SpringExtension.class)
public class TeachersControllerTest {
    @Autowired
    private TeachersController teachersController;

    @MockBean
    private TeachersService teachersService;

    @Test
    public void testFindAll() throws Exception {
        // Arrange
        when(this.teachersService.findAll()).thenReturn(new ArrayList<Teachers>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/teachers");
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.teachersController).build();

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
        when(this.teachersService.findAll()).thenReturn(new ArrayList<Teachers>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/teachers");
        getResult.contentType("Not all who wander are lost");
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.teachersController).build();

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
        when(this.teachersService.findById(anyInt())).thenReturn(new Teachers());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/teachers/{teachersID}", 1);
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.teachersController).build();

        // Act
        ResultActions actualPerformResult = buildResult.perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"teachersID\":0,\"firstName\":null,\"lastName\":null}"));
    }

    @Test
    public void testUpdate() throws Exception {
        // Arrange
        when(this.teachersService.update((Teachers) any())).thenReturn(new Result<Teachers>());

        Teachers teachers = new Teachers();
        teachers.setLastName("Doe");
        teachers.setTeachersID(1);
        teachers.setFirstName("Jane");
        String content = (new ObjectMapper()).writeValueAsString(teachers);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/teachers/{teachersID}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.teachersController).build();

        // Act
        ResultActions actualPerformResult = buildResult.perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testUpdate2() throws Exception {
        // Arrange
        when(this.teachersService.update((Teachers) any())).thenReturn(new Result<Teachers>());

        Teachers teachers = new Teachers();
        teachers.setLastName("Doe");
        teachers.setTeachersID(0);
        teachers.setFirstName("Jane");
        String content = (new ObjectMapper()).writeValueAsString(teachers);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/teachers/{teachersID}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.teachersController).build();

        // Act
        ResultActions actualPerformResult = buildResult.perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(409));
    }

    @Test
    public void testAdd() throws Exception {
        // Arrange
        when(this.teachersService.findAll()).thenReturn(new ArrayList<Teachers>());

        Teachers teachers = new Teachers();
        teachers.setLastName("Doe");
        teachers.setTeachersID(1);
        teachers.setFirstName("Jane");
        String content = (new ObjectMapper()).writeValueAsString(teachers);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/teachers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.teachersController).build();

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
        when(this.teachersService.deleteById(anyInt())).thenReturn(true);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/teachers/{teachersID}", 1);
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.teachersController).build();

        // Act
        ResultActions actualPerformResult = buildResult.perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testDeleteById2() throws Exception {
        // Arrange
        when(this.teachersService.deleteById(anyInt())).thenReturn(false);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/teachers/{teachersID}", 1);
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.teachersController).build();

        // Act
        ResultActions actualPerformResult = buildResult.perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testDeleteById3() throws Exception {
        // Arrange
        when(this.teachersService.deleteById(anyInt())).thenReturn(true);
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/teachers/{teachersID}", 1);
        deleteResult.contentType("Not all who wander are lost");
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.teachersController).build();

        // Act
        ResultActions actualPerformResult = buildResult.perform(deleteResult);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}

