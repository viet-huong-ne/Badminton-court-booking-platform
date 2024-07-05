package com.SWP.BadmintonCourtBooking.Repository;

import com.SWP.BadmintonCourtBooking.Entity.RecurringBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;

@Repository
public interface RecurringBookingRepository extends JpaRepository<RecurringBooking, Integer> {
    @Query("SELECT re FROM RecurringBooking re WHERE re.court.courtID = :courtID AND ((:startTime >= re.startTime AND :startTime < re.endTime) OR (:endTime > re.startTime AND :endTime <= re.endTime) OR (:startTime <= re.startTime AND :endTime >= re.endTime))")
    List<RecurringBooking> findRecuByCourtID(@Param("courtID") int courtID, @Param("startTime") LocalTime startTime, @Param("endTime") LocalTime endTime);
}
