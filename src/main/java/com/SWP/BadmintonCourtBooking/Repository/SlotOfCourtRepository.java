package com.SWP.BadmintonCourtBooking.Repository;

import com.SWP.BadmintonCourtBooking.Dto.SlotOfCourtDto;
import com.SWP.BadmintonCourtBooking.Entity.SlotOfCourt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SlotOfCourtRepository extends JpaRepository<SlotOfCourt, Integer> {
//    @Query("SELECT sl.openTime, sl.closeTime, sl.activeStatus FROM SlotOfCourt sl WHERE sl.court.courtID = :courtID")
//    List<SlotOfCourtDto> getSlotOfCourtByCourtID(@Param("courtID") int courtID);
@Query("SELECT sl FROM SlotOfCourt sl WHERE sl.court.courtID = :courtID")
List<SlotOfCourt> getSlotOfCourtByCourtID(@Param("courtID") int courtID);
}
