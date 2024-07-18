package com.SWP.BadmintonCourtBooking.Exception;

public enum ErrorCode {
    //Message lỗi
    USER_EXISTED("User Existed"),
    UNCATEGORIZED_EXCEPTION("Uncategori_exception"),
    USERNAME_INVALID("Username must be at least 6 characters"),
    INVALID_PASSWORD("Password must be at least 8 characters"),
    INVALID_KEY("Invalid key"),
    USER_NOT_EXISTED("User Not Existed"),
    UNAUTHENTICATED("Unauthenticated"),
    BOOKING_NOT_FOUND("Booking Not Found"),
    STAFF_NOT_FOUND("Staff Not Found"),
    COURT_NOT_FOUND("Court Not Found"),
    ;
    //Property
    private String message;

    //Getter && Setter
    ErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
