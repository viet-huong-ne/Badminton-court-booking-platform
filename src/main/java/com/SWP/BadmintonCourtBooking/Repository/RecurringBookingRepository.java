package com.SWP.BadmintonCourtBooking.Repository;

import com.SWP.BadmintonCourtBooking.Entity.RecurringBooking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecurringBookingRepository extends JpaRepository<RecurringBooking, Integer> {
}
