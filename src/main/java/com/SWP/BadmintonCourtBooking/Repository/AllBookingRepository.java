package com.SWP.BadmintonCourtBooking.Repository;

import com.SWP.BadmintonCourtBooking.Entity.AllBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AllBookingRepository extends JpaRepository<AllBooking, Integer> {
    @Query("SELECT a FROM AllBooking a WHERE a.courtID = :courtID")
    List<AllBooking> findAllBookingByCourtID(@Param("courtID") int courtID);
}
