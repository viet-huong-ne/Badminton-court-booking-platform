package com.SWP.BadmintonCourtBooking.Repository;

import com.SWP.BadmintonCourtBooking.Entity.BookingDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.util.Date;
import java.util.List;

@Repository
public interface BookingDetailsRepository extends JpaRepository<BookingDetails, Integer> {
    @Query("SELECT bd FROM BookingDetails bd WHERE bd.booking.BookingID = :bookingID")
    List<BookingDetails> findBookingDetailsByBookingID(@Param("bookingID") int bookingID);

    //    @Query("SELECT bd FROM BookingDetails bd WHERE :endTime < bd.StartTime OR bd.EndTime < :startTime")
//    List<BookingDetails> findOverlappingBookings(@Param("startTime") Time startTime, @Param("endTime") Time endTime);
    @Query("SELECT bd FROM BookingDetails bd WHERE bd.booking.booking_date = :bookingDate AND bd.booking.court.courtID = :courtID AND ((:startTime >= bd.StartTime AND :startTime < bd.EndTime) OR (:endTime > bd.StartTime AND :endTime <= bd.EndTime))")
    List<BookingDetails> findExistingTime(@Param("startTime") Time startTime, @Param("endTime") Time endTime, @Param("courtID") int courtID, @Param("bookingDate") Date bookingDate);
}
//if (endTime.after(x.getStartTime()) || startTime.before(x.getEndTime()) || (startTime.equals(x.getStartTime())) ||
// ((startTime.equals(x.getStartTime()) || startTime.after(x.getStartTime())) & (endTime.equals(x.getEndTime()) || endTime.before(x.getEndTime()))))