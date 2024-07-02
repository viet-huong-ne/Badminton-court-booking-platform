package com.SWP.BadmintonCourtBooking.Repository;

import com.SWP.BadmintonCourtBooking.Entity.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;
@Repository
public interface PriceRepository extends JpaRepository<Price, Integer> {

    @Query("SELECT p FROM Price p WHERE p.court.courtID = :courtID ")
    List<Price> getPriceByCourtID(@Param("courtID") int courtID);
    @Query("SELECT p.unitPrice FROM Price p WHERE p.court.courtID = :courtID AND (:startTime >= p.openTime AND :endTime <= p.closeTime)")
    Double getPriceOfSlot(@Param("courtID") int courtID, @Param("startTime") LocalTime startTime, @Param("endTime") LocalTime endTime);
}
