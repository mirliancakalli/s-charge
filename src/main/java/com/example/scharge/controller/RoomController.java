package com.example.scharge.controller;

import com.example.scharge.dto.RoomDTO;
import com.example.scharge.service.RoomService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public ResponseEntity<List<RoomDTO>> rooms(
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) LocalDateTime startTime,
            @RequestParam(required = false) LocalDateTime endTime,
            @RequestParam(required = false) boolean isBooked) {

        List<RoomDTO> availableRooms = roomService.listAvailableRooms(maxPrice, startTime, endTime,isBooked);
        return ResponseEntity.ok(availableRooms);
    }

    @PostMapping("/book")
    public ResponseEntity<RoomDTO> bookRoom(@Valid @RequestBody RoomDTO roomDTO) {
        RoomDTO bookedRoom = roomService.bookRoom(roomDTO);
        return ResponseEntity.ok(bookedRoom);
    }

    @DeleteMapping("/cancel/{roomId}")
    public ResponseEntity<Void> cancelBooking(@PathVariable String roomId) {
        roomService.cancelBooking(roomId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/history")
    public ResponseEntity<List<RoomDTO>> getBookingHistory() {
        List<RoomDTO> bookingHistory = roomService.getBookingHistory()
                .stream()
                .filter(room -> !room.isCanceled())
                .collect(Collectors.toList());
        return ResponseEntity.ok(bookingHistory);
    }

    @GetMapping("/history/print")
    public void printBookingHistoryToConsole() {
        roomService.printBookingHistoryToConsole();
    }

    @GetMapping("/history/filter")
    public ResponseEntity<List<RoomDTO>> filterBookingHistory(
            @RequestParam(required = false) LocalDateTime startTime,
            @RequestParam(required = false) LocalDateTime endTime,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice) {

        List<RoomDTO> filteredHistory = roomService.filterBookingHistory(startTime, endTime, minPrice, maxPrice);
        return ResponseEntity.ok(filteredHistory);
    }

}
