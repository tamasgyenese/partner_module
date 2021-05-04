package com.partner.common;

public final class Constants {


    public static final String TOKEN_HEADER = "token";
    public static final String API_KEY_HEADER_PARAMETER = "API_KEY";

    public static final String URL_PATH_GET_EVENTS = "/ticket/getEvents";
    public static final String URL_PATH_GET_EVENT_BY_ID = "/ticket/getEvent";
    public static final String URL_PATH_POST_RESERVATION_BASE = "/ticket/reserve";
    public static final String URL_PATH_POST_RESERVATION_EVENT_ID = "/eventId";
    public static final String URL_PATH_POST_RESERVATION_SEAT_ID = "/seatId";
    public static final String URL_PATH_POST_RESERVATION_CARD_ID = "/cardId";

    public static final int HTTP_STATUS_OK_MIN = 200;
    public static final int HTTP_STATUS_OK_MAX = 299;


    public static final String AUTHENTICATION_HASH = "a85a9a789b3d926e6cbd0e3ec0354ca5f7808b2334a3ecbdec469685942d6d60";
}
