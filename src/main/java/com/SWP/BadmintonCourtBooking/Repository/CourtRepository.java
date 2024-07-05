package com.SWP.BadmintonCourtBooking.Repository;

import com.SWP.BadmintonCourtBooking.Entity.Court;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourtRepository extends JpaRepository<Court, Integer> {
    @Query("SELECT c FROM Court c WHERE c.district = :district")
    List<Court> getCourtByDistrict(@Param("district") String district);

    @Query("SELECT c FROM Court c WHERE c.user.userID = :userID")
    List<Court> getCourtByUserID(@Param("userID") int userID);
}
