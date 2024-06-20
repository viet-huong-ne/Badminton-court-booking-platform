package com.SWP.BadmintonCourtBooking.Dto.Respone;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class APIRespone <T> { //chuẩn hóa API trả về
    //property
    private String message;
    private T result; //Kiểu trả về là generic
}
