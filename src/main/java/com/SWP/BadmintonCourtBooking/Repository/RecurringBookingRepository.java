package com.SWP.BadmintonCourtBooking.Repository;

import com.SWP.BadmintonCourtBooking.Entity.RecurringBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface RecurringBookingRepository extends JpaRepository<RecurringBooking, Integer> {
    @Query("SELECT re FROM RecurringBooking re WHERE re.court.courtID = :courtID AND ((:startTime >= re.startTime AND :startTime < re.endTime) OR (:endTime > re.startTime AND :endTime <= re.endTime) OR (:startTime <= re.startTime AND :endTime >= re.endTime))")
    List<RecurringBooking> findRecuByCourtID(@Param("courtID") int courtID, @Param("startTime") LocalTime startTime, @Param("endTime") LocalTime endTime);
//    @Query("SELECT rb FROM RecurringBooking rb WHERE rb.court.courtID = :courtId " +
//            "AND rb.startDate <= :endDate AND rb.endDate >= :startDate " +
//            "AND EXISTS (SELECT 1 FROM RecurringBooking rbd WHERE rbd.id = rb.id " +
//            "AND (rbd.startTime < :endTime AND rbd.endTime > :startTime) " +
//            "AND (rbd.daysOfWeek IN :daysOfWeek))")
//    List<RecurringBooking> findRecurringBookingsWithinDateRangeAndTime(
//            @Param("courtId") int courtId,
//            @Param("startDate") LocalDate startDate,
//            @Param("endDate") LocalDate endDate,
//            @Param("startTime") LocalTime startTime,
//            @Param("endTime") LocalTime endTime,
//            @Param("daysOfWeek") List<String> daysOfWeek
//    );
@Query(value = "SELECT rb.* FROM recurring_booking rb " +
        "WHERE rb.court_id = :courtId " +
        "AND rb.start_date <= :endDate " +
        "AND rb.end_date >= :startDate " +
        "AND EXISTS ( " +
        "    SELECT 1 FROM recurring_booking rbd " +
        "    JOIN recurring_booking_days rbd_days ON rbd.id = rbd_days.recurring_booking_id " +
        "    WHERE rbd.id = rb.id " +
        "    AND rbd.start_time < :endTime " +
        "    AND rbd.end_time > :startTime " +
        "    AND rbd_days.day_of_week IN (:daysOfWeek) " +
        ")", nativeQuery = true)
List<RecurringBooking> findRecurringBookingsWithinDateRangeAndTime(
        @Param("courtId") int courtId,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate,
        @Param("startTime") LocalTime startTime,
        @Param("endTime") LocalTime endTime,
        @Param("daysOfWeek") List<String> daysOfWeek
);
//    @Query("SELECT rb FROM RecurringBooking rb WHERE rb.court.courtID = :courtId " +
//            "AND rb.startDate <= :endDate AND rb.endDate >= :startDate")
//    List<RecurringBooking> findRecurringBookingsWithinDateRange(
//            @Param("courtId") int courtId,
//            @Param("startDate") LocalDate startDate,
//            @Param("endDate") LocalDate endDate
//    );
}
