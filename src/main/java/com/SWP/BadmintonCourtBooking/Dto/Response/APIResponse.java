package com.SWP.BadmintonCourtBooking.Dto.Response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class APIResponse <T> { //chuẩn hóa API trả về
    //property
    private String message;
    private T result; //Kiểu trả về là generic
}