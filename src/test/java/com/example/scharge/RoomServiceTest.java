package com.example.scharge;

import com.example.scharge.dto.RoomDTO;
import com.example.scharge.entity.Room;
import com.example.scharge.repository.RoomRepository;
import com.example.scharge.service.RoomService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class RoomServiceTest {

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private RoomService roomService;

    @Test
    public void testListAvailableRooms() {
        when(roomRepository.findAvailableRooms(any(BigDecimal.class), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Collections.emptyList());

        List<RoomDTO> availableRooms = roomService.listAvailableRooms(BigDecimal.TEN, LocalDateTime.now(), LocalDateTime.now().plusHours(1));
        assertThat(availableRooms).isEmpty();
    }

    @Test
    public void testBookRoom() {
        when(roomRepository.save(any(Room.class))).thenReturn(new Room());

        RoomDTO bookedRoom = roomService.bookRoom(new RoomDTO());
        assertThat(bookedRoom).isNotNull();
    }
}

