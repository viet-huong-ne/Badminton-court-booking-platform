package com.SWP.BadmintonCourtBooking.Repository;

import com.SWP.BadmintonCourtBooking.Entity.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PriceRepository extends JpaRepository<Price, Integer> {

    @Query("SELECT p FROM Price p WHERE p.court.courtID = :courtID ")
    List<Price> getPriceByCourtID(@Param("courtID") int courtID);
}
