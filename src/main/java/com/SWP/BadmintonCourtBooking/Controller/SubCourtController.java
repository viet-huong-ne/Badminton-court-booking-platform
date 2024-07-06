package com.SWP.BadmintonCourtBooking.Controller;

import com.SWP.BadmintonCourtBooking.Entity.SubCourt;
import com.SWP.BadmintonCourtBooking.Service.SubCourtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/subcourt")
public class SubCourtController {

    @Autowired
    private SubCourtService subCourtService;


    @GetMapping("/{subCourtID}")
    public SubCourt getSubCourt(@PathVariable int subCourtID) {
        SubCourt subCourt = subCourtService.getSubCourtById(subCourtID);
        return subCourt;
    }
}
