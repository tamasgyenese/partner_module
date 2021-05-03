package com.partner.servicecaller;


import com.partner.exception.NoResponseDataException;
import com.partner.model.Event;
import com.partner.model.Reservation;
import com.partner.model.ServiceResponse;

import java.util.List;

public interface IServiceCaller {

    List<Event> getEvents(String url, String token) throws NoResponseDataException;

    Event getEvent(String url, String token, long id) throws NoResponseDataException;

    ServiceResponse<Reservation> reserve(String url, String token, long eventId, String seatId, String cardId) throws NoResponseDataException;

}
