package com.example.scharge.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "rooms")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomNumber;
    private BigDecimal price;
    private LocalDateTime bookedStartTime;
    private LocalDateTime bookedEndTime;
    private boolean canceled;

    public Room(String roomNumber, BigDecimal price, LocalDateTime bookedStartTime, LocalDateTime bookedEndTime) {
        this.roomNumber = roomNumber;
        this.price = price;
        this.bookedStartTime = bookedStartTime;
        this.bookedEndTime = bookedEndTime;
    }
}
