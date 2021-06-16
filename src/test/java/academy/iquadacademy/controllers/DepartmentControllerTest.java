package academy.iquadacademy.controllers;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;

import academy.iquadacademy.domain.DepartmentService;
import academy.iquadacademy.domain.Result;
import academy.iquadacademy.models.Department;
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

@ContextConfiguration(classes = {DepartmentController.class})
@ExtendWith(SpringExtension.class)
public class DepartmentControllerTest {
    @Autowired
    private DepartmentController departmentController;

    @MockBean
    private DepartmentService departmentService;

    @Test
    public void testFindAll() throws Exception {
        // Arrange
        when(this.departmentService.findAll()).thenReturn(new ArrayList<Department>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/department");
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.departmentController).build();

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
        when(this.departmentService.findAll()).thenReturn(new ArrayList<Department>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/department");
        getResult.contentType("Not all who wander are lost");
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.departmentController).build();

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
        when(this.departmentService.findById(anyInt())).thenReturn(new Department());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/department/{depID}", 1);
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.departmentController).build();

        // Act
        ResultActions actualPerformResult = buildResult.perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"depID\":0,\"depName\":null,\"depCode\":0}"));
    }

    @Test
    public void testUpdate() throws Exception {
        // Arrange
        when(this.departmentService.update((Department) any())).thenReturn(new Result<Department>());

        Department department = new Department();
        department.setDepID(1);
        department.setDepName("Dep Name");
        department.setDepCode(1);
        String content = (new ObjectMapper()).writeValueAsString(department);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/department/{depID}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.departmentController).build();

        // Act
        ResultActions actualPerformResult = buildResult.perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testUpdate2() throws Exception {
        // Arrange
        when(this.departmentService.update((Department) any())).thenReturn(new Result<Department>());

        Department department = new Department();
        department.setDepID(0);
        department.setDepName("Dep Name");
        department.setDepCode(1);
        String content = (new ObjectMapper()).writeValueAsString(department);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/department/{depID}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.departmentController).build();

        // Act
        ResultActions actualPerformResult = buildResult.perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(409));
    }

    @Test
    public void testAdd() throws Exception {
        // Arrange
        when(this.departmentService.findAll()).thenReturn(new ArrayList<Department>());

        Department department = new Department();
        department.setDepID(1);
        department.setDepName("Dep Name");
        department.setDepCode(1);
        String content = (new ObjectMapper()).writeValueAsString(department);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/department")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.departmentController).build();

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
        when(this.departmentService.deleteById(anyInt())).thenReturn(true);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/department/{depID}", 1);
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.departmentController).build();

        // Act
        ResultActions actualPerformResult = buildResult.perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testDeleteById2() throws Exception {
        // Arrange
        when(this.departmentService.deleteById(anyInt())).thenReturn(false);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/department/{depID}", 1);
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.departmentController).build();

        // Act
        ResultActions actualPerformResult = buildResult.perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testDeleteById3() throws Exception {
        // Arrange
        when(this.departmentService.deleteById(anyInt())).thenReturn(true);
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/department/{depID}", 1);
        deleteResult.contentType("Not all who wander are lost");
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.departmentController).build();

        // Act
        ResultActions actualPerformResult = buildResult.perform(deleteResult);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}

