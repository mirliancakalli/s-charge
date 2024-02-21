package com.example.scharge.service;


import com.example.scharge.dto.RoomDTO;
import com.example.scharge.entity.Room;
import com.example.scharge.exceptionhandler.BookingException;
import com.example.scharge.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class RoomService {

    private static final Logger logger = LoggerFactory.getLogger(RoomService.class);

    private final RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<RoomDTO> listAvailableRooms(BigDecimal maxPrice, LocalDateTime startTime, LocalDateTime endTime) {
        List<Room> availableRooms = roomRepository.findAvailableRooms(maxPrice, startTime, endTime);
        return availableRooms.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public RoomDTO bookRoom(RoomDTO roomDTO) {
        Room bookedRoom = roomRepository.save(convertToEntity(roomDTO));
        //since filtered through available rooms I am skipping a check to see if room is free or not.
        logger.info("room with id: "+bookedRoom.getId()+", booked!");
        return convertToDTO(bookedRoom);
    }

    public void cancelBooking(Long roomId) {
        Room roomToCancel = roomRepository.findById(roomId)
                .orElseThrow(() -> new BookingException("Room not found for cancellation"));
        logger.info("room with id: "+roomToCancel.getId()+", canceled!");
        roomToCancel.setCanceled(true);
        roomRepository.save(roomToCancel);
    }

    public List<RoomDTO> getBookingHistory() {
        List<Room> bookingHistory = roomRepository.findAll();
        return bookingHistory.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public void printBookingHistoryToConsole() {
        List<RoomDTO> bookingHistory = getBookingHistory();
        bookingHistory.forEach(System.out::println);
    }

    public List<RoomDTO> filterBookingHistory(LocalDateTime startTime, LocalDateTime endTime, BigDecimal minPrice, BigDecimal maxPrice) {
        List<Room> filteredHistory = roomRepository.findFilteredBookingHistory(startTime, endTime, minPrice, maxPrice);
        return filteredHistory.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private RoomDTO convertToDTO(Room room) {
        return new RoomDTO(
                room.getId(),
                room.getRoomNumber(),
                room.getPrice(),
                room.getBookedStartTime(),
                room.getBookedEndTime()
        );
    }

    private Room convertToEntity(RoomDTO roomDTO) {
        return new Room(
                roomDTO.getRoomNumber(),
                roomDTO.getPrice(),
                roomDTO.getBookedStartTime(),
                roomDTO.getBookedEndTime()
        );
    }
}
