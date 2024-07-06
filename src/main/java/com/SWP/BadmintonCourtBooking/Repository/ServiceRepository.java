package com.SWP.BadmintonCourtBooking.Repository;

import com.SWP.BadmintonCourtBooking.Entity.ServiceCourt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceCourt, Integer> {
    @Query("SELECT s FROM ServiceCourt s WHERE s.serviceName = :serviceName")
    ServiceCourt findByServiceName(@Param("serviceName") String serviceName);

    /*
    @Query("SELECT s FROM ServiceCourt WHERE s = :serviceName")
    List<ServiceCourt> findAll();

     */
}
