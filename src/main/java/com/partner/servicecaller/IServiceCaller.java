package com.partner.servicecaller;


import com.partner.model.Event;

import java.util.List;

public interface IServiceCaller {

    List<Event> getEvents(String url, String token);

}
