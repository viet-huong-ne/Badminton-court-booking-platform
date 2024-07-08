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
@Query(value = "SELECT s.sub_court_id " +
        "FROM sub_court s " +
        "WHERE s.court_id = :courtId " +
        "AND s.sub_court_id NOT IN (" +
        "SELECT sc.sub_court_id " +
        "FROM recurring_booking rb " +
        "JOIN sub_court sc ON rb.court_id = sc.court_id " +
        "JOIN recurring_booking_days rbd ON rb.id = rbd.recurring_booking_id " +
        "WHERE rb.court_id = :courtId " +
        "AND rb.start_date <= :endDate " +
        "AND rb.end_time >= :startDate " +
        "AND rbd.day_of_week IN (:daysOfWeek) " +
        "AND rb.start_time < :endTime AND rb.end_time > :startTime)", nativeQuery = true)
List<Integer> findAvailableSubCourts(int courtId, LocalDate startDate, LocalDate endDate,
                                      List<DayOfWeek> daysOfWeek, LocalTime startTime, LocalTime endTime);
}
