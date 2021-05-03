package com.partner.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Objects;

/**
 * Mock POJO for API Response
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Event {

    private long eventId;
    private String title;
    private String location;
    private String startTimeStamp;
    private String endTimeStamp;

    private List<Seat> seats;

    public Event() {
    }

    public Event(long eventId) {
        this.eventId = eventId;
    }

    public Event(long eventId, String title, String location, String startTimeStamp, String endTimeStamp, List<Seat> seats) {
        this.eventId = eventId;
        this.title = title;
        this.location = location;
        this.startTimeStamp = startTimeStamp;
        this.endTimeStamp = endTimeStamp;
        this.seats = seats;
    }

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStartTimeStamp() {
        return startTimeStamp;
    }

    public void setStartTimeStamp(String startTimeStamp) {
        this.startTimeStamp = startTimeStamp;
    }

    public String getEndTimeStamp() {
        return endTimeStamp;
    }

    public void setEndTimeStamp(String endTimeStamp) {
        this.endTimeStamp = endTimeStamp;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return eventId == event.eventId &&
                Objects.equals(title, event.title) &&
                Objects.equals(location, event.location) &&
                Objects.equals(startTimeStamp, event.startTimeStamp) &&
                Objects.equals(endTimeStamp, event.endTimeStamp) &&
                Objects.equals(seats, event.seats);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, title, location, startTimeStamp, endTimeStamp, seats);
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventId=" + eventId +
                ", title='" + title + '\'' +
                ", location='" + location + '\'' +
                ", startTimeStamp=" + startTimeStamp +
                ", endTimeStamp=" + endTimeStamp +
                ", seats=" + seats +
                '}';
    }
}
