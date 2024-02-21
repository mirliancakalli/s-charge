package com.example.scharge.repository;

import com.example.scharge.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface RoomRepository extends JpaRepository<Room,Long> {
    @Query("SELECT r FROM Room r " +
            "WHERE (r.bookedStartTime IS NULL OR r.bookedStartTime > :endTime OR r.bookedEndTime < :startTime) " +
            "AND (:maxPrice IS NULL OR r.price <= :maxPrice)" +
            "AND r.booked = :isBooked")
    List<Room> findAvailableRooms(
            @Param("maxPrice") BigDecimal maxPrice,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime,
            boolean isBooked);

    @Query("SELECT r FROM Room r " +
            "WHERE (:startTime IS NULL OR r.bookedEndTime >= :startTime) " +
            "AND (:endTime IS NULL OR r.bookedStartTime <= :endTime) " +
            "AND (:minPrice IS NULL OR r.price >= :minPrice) " +
            "AND (:maxPrice IS NULL OR r.price <= :maxPrice)")
    List<Room> findFilteredBookingHistory(
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice
    );


    Room findByRoomNumber(String roomNumber);

}
