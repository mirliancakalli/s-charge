package com.example.scharge;

import com.example.scharge.controller.RoomController;
import com.example.scharge.dto.RoomDTO;
import com.example.scharge.service.RoomService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(RoomController.class)
public class RoomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoomService roomService;

    @Test
    public void testListAvailableRooms() throws Exception {
        when(roomService.listAvailableRooms(any(BigDecimal.class), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/rooms")
                        .param("maxPrice", "100.00")
                        .param("startTime", "2024-02-21T10:00:00")
                        .param("endTime", "2024-02-21T12:00:00")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testBookRoom() throws Exception {
        when(roomService.bookRoom(any(RoomDTO.class))).thenReturn(new RoomDTO());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/rooms/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}