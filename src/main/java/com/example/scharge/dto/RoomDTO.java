package com.example.scharge.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class RoomDTO {

    @Null(message = "Id must be null for new room creation")
    private Long id;
    @NotBlank(message = "Room number is required")
    private String roomNumber;
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.01", message = "Price must be greater than or equal to 0.01")
    private BigDecimal price;
    private LocalDateTime bookedStartTime;
    private LocalDateTime bookedEndTime;
    private boolean canceled;

    public RoomDTO(Long id, String roomNumber, BigDecimal price, LocalDateTime bookedStartTime, LocalDateTime bookedEndTime) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.price = price;
        this.bookedStartTime = bookedStartTime;
        this.bookedEndTime = bookedEndTime;
    }

    @AssertTrue(message = "Booked start time must be before booked end time")
    private boolean isBookedTimeValid() {
        return bookedStartTime == null || bookedEndTime == null || bookedStartTime.isBefore(bookedEndTime);
    }
}
