package com.SWP.BadmintonCourtBooking.Repository;


import com.SWP.BadmintonCourtBooking.Entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

}

