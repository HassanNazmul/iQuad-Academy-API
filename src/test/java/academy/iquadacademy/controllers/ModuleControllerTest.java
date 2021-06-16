package academy.iquadacademy.controllers;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;

import academy.iquadacademy.domain.ModuleService;
import academy.iquadacademy.domain.Result;
import academy.iquadacademy.models.Module;
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

@ContextConfiguration(classes = {ModuleController.class})
@ExtendWith(SpringExtension.class)
public class ModuleControllerTest {
    @Autowired
    private ModuleController moduleController;

    @MockBean
    private ModuleService moduleService;

    @Test
    public void testFindAll() throws Exception {
        // Arrange
        when(this.moduleService.findAll()).thenReturn(new ArrayList<Module>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/module");
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.moduleController).build();

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
        when(this.moduleService.findAll()).thenReturn(new ArrayList<Module>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/module");
        getResult.contentType("Not all who wander are lost");
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.moduleController).build();

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
        when(this.moduleService.findById(anyInt())).thenReturn(new Module());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/module/{modID}", 1);
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.moduleController).build();

        // Act
        ResultActions actualPerformResult = buildResult.perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"modID\":0,\"modName\":null,\"modCode\":0,\"depID\":0}"));
    }

    @Test
    public void testUpdate() throws Exception {
        // Arrange
        when(this.moduleService.update((Module) any())).thenReturn(new Result<Module>());

        Module resultModule = new Module();
        resultModule.setModCode(1);
        resultModule.setDepID(1);
        resultModule.setModName("Mod Name");
        resultModule.setModID(1);
        String content = (new ObjectMapper()).writeValueAsString(resultModule);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/module/{modID}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.moduleController).build();

        // Act
        ResultActions actualPerformResult = buildResult.perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testUpdate2() throws Exception {
        // Arrange
        when(this.moduleService.update((Module) any())).thenReturn(new Result<Module>());

        Module resultModule = new Module();
        resultModule.setModCode(1);
        resultModule.setDepID(1);
        resultModule.setModName("Mod Name");
        resultModule.setModID(0);
        String content = (new ObjectMapper()).writeValueAsString(resultModule);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/module/{modID}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.moduleController).build();

        // Act
        ResultActions actualPerformResult = buildResult.perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(409));
    }

    @Test
    public void testAdd() throws Exception {
        // Arrange
        when(this.moduleService.findAll()).thenReturn(new ArrayList<Module>());

        Module resultModule = new Module();
        resultModule.setModCode(1);
        resultModule.setDepID(1);
        resultModule.setModName("Mod Name");
        resultModule.setModID(1);
        String content = (new ObjectMapper()).writeValueAsString(resultModule);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/module")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.moduleController).build();

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
        when(this.moduleService.deleteById(anyInt())).thenReturn(true);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/module/{modID}", 1);
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.moduleController).build();

        // Act
        ResultActions actualPerformResult = buildResult.perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testDeleteById2() throws Exception {
        // Arrange
        when(this.moduleService.deleteById(anyInt())).thenReturn(false);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/module/{modID}", 1);
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.moduleController).build();

        // Act
        ResultActions actualPerformResult = buildResult.perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testDeleteById3() throws Exception {
        // Arrange
        when(this.moduleService.deleteById(anyInt())).thenReturn(true);
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/module/{modID}", 1);
        deleteResult.contentType("Not all who wander are lost");
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.moduleController).build();

        // Act
        ResultActions actualPerformResult = buildResult.perform(deleteResult);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}

