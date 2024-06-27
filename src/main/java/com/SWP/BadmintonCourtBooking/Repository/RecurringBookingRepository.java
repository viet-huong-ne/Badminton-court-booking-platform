package com.SWP.BadmintonCourtBooking.Repository;

import com.SWP.BadmintonCourtBooking.Entity.RecurringBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecurringBookingRepository extends JpaRepository<RecurringBooking, Integer> {
    @Query("SELECT re FROM RecurringBooking re WHERE re.court.courtID = :courtID ")
    public List<RecurringBooking> findByCourtID(Integer courtID);
}
