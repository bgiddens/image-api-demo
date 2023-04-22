package org.heb.api.images.controllers;

import org.heb.api.images.exceptions.NotFoundException;
import org.heb.api.images.services.ImageService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class ErrorHandlingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ImageService imageService;

    @Test
    void getImage_returns_404_on_not_found() throws Exception {
        var id = UUID.randomUUID();
        Mockito.when(imageService.findById(id)).thenThrow(new NotFoundException());
        mockMvc.perform(MockMvcRequestBuilders.get(String.format("/images/%s", id)))
                .andExpect(status().isNotFound())
                .andDo(print())
                .andReturn();
    }

    @Test
    void getImage_returns_400_on_bad_id() throws Exception {
        var id = "invalid!";
        mockMvc.perform(MockMvcRequestBuilders.get(String.format("/images/%s", id)))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andReturn();
    }


}
