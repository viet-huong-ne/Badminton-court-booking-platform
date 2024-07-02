package com.SWP.BadmintonCourtBooking.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;

public interface GoogleCalendarService {
    void addEvent(String summary, String description, String location, String startDateTime, String endDateTime) throws IOException, GeneralSecurityException;
}
