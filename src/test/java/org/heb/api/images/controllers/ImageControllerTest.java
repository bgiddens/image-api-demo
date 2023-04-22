package org.heb.api.images.controllers;

import org.hamcrest.Matchers;
import org.heb.api.images.models.Image;
import org.heb.api.images.services.ImageService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.argThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class ImageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ImageService imageService;

    @Test
    void getImages_calls_service_layer_and_returns_ok() throws Exception {
        Mockito.when(imageService.search(null)).thenReturn(List.of());
        mockMvc.perform(MockMvcRequestBuilders.get("/images"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

    @Test
    void getImage_calls_service_layer_and_returns_ok() throws Exception {
        var id = UUID.randomUUID();
        var image = new Image();
        image.setId(id);
        Mockito.when(imageService.findById(id)).thenReturn(image);
        mockMvc.perform(MockMvcRequestBuilders.get(String.format("/images/%s", id)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.equalTo(id.toString())))
                .andDo(print())
                .andReturn();
    }

    @Test
    void createImage_calls_service_layer_and_returns_content() throws Exception {
        var id = UUID.randomUUID();
        var image = new Image();
        image.setId(id);
        Mockito.when(imageService.create(argThat(i -> "foo".equals(i.getLocation())))).thenReturn(image);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/images")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"location\":\"foo\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.equalTo(id.toString())))
                .andDo(print())
                .andReturn();
    }

}
