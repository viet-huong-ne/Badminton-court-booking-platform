package com.SWP.BadmintonCourtBooking.Repository;

import com.SWP.BadmintonCourtBooking.Entity.Booking;
import com.SWP.BadmintonCourtBooking.Entity.SubCourt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
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

    //    @Query("SELECT s FROM SubCourt s WHERE s.court.courtID = :courtId AND s.SubCourtID NOT IN " +
//            "(SELECT rb FROM RecurringBooking rb WHERE rb.court.courtID = :courtId AND rb.startDate <= :endDate AND rb.endDate >= :startDate " +
//            "AND rb.daysOfWeek IN :daysOfWeek AND rb.startTime < :endTime AND rb.endTime > :startTime)")
//@Query(value = "SELECT s FROM SubCourt s WHERE s.court.courtID = :courtId AND s.SubCourtID NOT IN " +
//        "(SELECT sc.SubCourtID FROM RecurringBooking rb JOIN rb.subCourts sc " +
//        "WHERE rb.court.courtID = :courtId AND rb.startDate <= :endDate AND rb.endDate >= :startDate " +
//        "AND rb.daysOfWeek IN :daysOfWeek AND rb.startTime < :endTime AND rb.endTime > :startTime)", nativeQuery = true)
//    List<SubCourt> findAvailableSubCourts(int courtId, LocalDate startDate, LocalDate endDate,
//                                          List<DayOfWeek> daysOfWeek, LocalTime startTime, LocalTime endTime);
    @Query("SELECT b FROM Booking b WHERE (b.bookingDate BETWEEN :startOfWeek AND :endOfWeek) AND b.court.courtID = :courtID    ")
    List<Booking> findBookingsWithinCurrentWeek(@Param("startOfWeek") LocalDate startOfWeek, @Param("endOfWeek") LocalDate endOfWeek, @Param("courtID") int courtID);

    @Query("SELECT b FROM Booking b WHERE b.court.user.userID = :userid")
    List<Booking> findByCourtOnwerID(@Param("userid") Integer userID);
}
