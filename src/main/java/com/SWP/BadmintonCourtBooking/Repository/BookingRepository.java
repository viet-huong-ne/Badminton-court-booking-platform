package com.SWP.BadmintonCourtBooking.Repository;

import com.SWP.BadmintonCourtBooking.Entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    @Query("SELECT b FROM Booking b WHERE b.user.userID = :userid")
    List<Booking> findByUserID(@Param("userid") Integer userID);
    @Query("SELECT b FROM Booking b WHERE b.court.courtID = :courtID")
    List<Booking> findByCourtID(@Param("courtID") Integer courtID);
    @Query("SELECT b FROM Booking b WHERE b.bookingDate = :booking_date AND b.court.courtID = :courtID")
    List<Booking> findByBookingDate(@Param("booking_date") LocalDate booking_date, @Param("courtID") int courtID);
}
