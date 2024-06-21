package com.SWP.BadmintonCourtBooking.Repository;

import com.SWP.BadmintonCourtBooking.Dto.SubCourtDto;
import com.SWP.BadmintonCourtBooking.Entity.SubCourt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.swing.*;
import java.util.List;

@Repository
public interface SubCourtRepository extends JpaRepository<SubCourt, Integer> {
//    @Query("SELECT su.SubCourtName, su.SubCourtStatus FROM SubCourt su WHERE su.court.courtID = :courtID ")
//    List<SubCourtDto> getSubCourtByCourtID(@Param("courtID") int courtID);
    @Query("SELECT su FROM SubCourt su WHERE su.court.courtID = :courtID ")
    List<SubCourt> getSubCourtByCourtID(@Param("courtID") int courtID);
}
