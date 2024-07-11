package com.SWP.BadmintonCourtBooking.Repository;

import com.SWP.BadmintonCourtBooking.Entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Integer> {
    @Query("SELECT s FROM Staff s WHERE s.user.userID = :userID")
    Staff findCourtIDByUserID(int userID);
}
