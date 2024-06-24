package com.SWP.BadmintonCourtBooking.Repository;


import com.SWP.BadmintonCourtBooking.Entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    //    @Query("SELECT p FROM Payment p WHERE p.userId = :userId ORDER BY ABS(FUNCTION('TIMESTAMPDIFF', 'SECOND', p.paymentTime, :currentTime)) ASC")
//    Payment findNearestPaymentByUserId(@Param("userId") int userId, @Param("currentTime") LocalDateTime currentTime);
    @Query(value = "SELECT * FROM Payment p WHERE p.user_id = :userId ORDER BY ABS(TIMESTAMPDIFF(MINUTE, p.payment_time, :currentTime)) ASC LIMIT 5", nativeQuery = true)
    Payment findNearestPaymentByUserId(@Param("userId") int userId, @Param("currentTime") LocalDateTime currentTime);
    @Query("SELECT p FROM Payment p WHERE p.userId = :userId AND p.trasactionCode = :trasactionCode")
    Payment findByUserIdAndPayCode(@Param("userId") int userId, @Param("trasactionCode") String trasactionCode );
}

