package com.SWP.BadmintonCourtBooking.Repository;


import com.SWP.BadmintonCourtBooking.Dto.RevenueDTO;
import com.SWP.BadmintonCourtBooking.Entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    @Query("SELECT p FROM Payment p WHERE p.booking.court.courtID = :courtID")
//    @Query(value = "SELECT p.* FROM payment p " +
//            "LEFT JOIN booking b ON p.booking_id = b.booking_id " +
//            "LEFT JOIN court c ON b.court_id = c.court_id " +
//            "LEFT JOIN recurring_booking rb ON p.recurring_booking_id = rb.id " +
//            "LEFT JOIN court rc ON rb.court_id = rc.court_id WHERE c.court_id = :courtID OR rc.court_id = :courtID", nativeQuery = true)
    List<Payment> findByCourtID(@Param("courtID") int courtID);

    @Query("SELECT p FROM Payment p WHERE p.recurringBooking.court.courtID = :courtID")
    List<Payment> findByCourtIDV2(@Param("courtID") int courtID);
    @Query("SELECT p.transactionCode FROM Payment p WHERE p.transactionCode = :transactionCode")
    String findTransactionCode(@Param("transactionCode") String transactionCode);

//    @Query("SELECT SUM(p.paymentAmount) FROM Payment p WHERE DATE(p.paymentTime) = DATE(:date) AND p.booking.court.courtID = :courtID")
//    Double findDailyRevenue(LocalDateTime date);
//
//    @Query("SELECT SUM(p.paymentAmount) FROM Payment p WHERE YEAR(p.paymentTime) = YEAR(:date) AND MONTH(p.paymentTime) = MONTH(:date) AND p.booking.court.courtID = :courtID")
//    Double findMonthlyRevenue(LocalDateTime date);
//
//    @Query("SELECT SUM(p.paymentAmount) FROM Payment p WHERE YEAR(p.paymentTime) = YEAR(:date) AND p.booking.court.courtID = :courtID")
//    Double findYearlyRevenue(LocalDateTime date);

    @Query("SELECT p FROM Payment p WHERE p.booking.court.courtID = :courtID AND p.paymentTime BETWEEN :startDate AND :endDate")
    List<Payment> findPaymentsByDateRange(@Param("courtID")int courtID,@Param("startDate") LocalDateTime startDate,@Param("endDate") LocalDateTime endDate);

//    @Query("SELECT p FROM Payment p WHERE p.booking.court.courtID = :courtID AND YEAR(p.paymentTime) = YEAR(:date) AND MONTH(p.paymentTime) = MONTH(:date)")
//    List<Payment> findPaymentsByMonth(@Param("courtID")int courtID,@Param("date") LocalDateTime date);

    @Query("SELECT p FROM Payment p WHERE p.booking.court.courtID = :courtID AND YEAR(p.paymentTime) = YEAR(:date)")
    List<Payment> findPaymentsByYear(@Param("courtID")int courtID,@Param("date") LocalDateTime date);
}

