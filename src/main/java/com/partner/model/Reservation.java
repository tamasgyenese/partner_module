package com.partner.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Objects;
import java.util.Random;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Reservation {

    private long reservationID;
    private boolean success;
    private int errorCode;

    public Reservation() {
    }

    public Reservation(boolean success) {
        this.success = success;
        if (success) {
            Random random = new Random();
            this.reservationID = Math.abs(random.nextLong());
        }
    }

    public Reservation(boolean success, int errorCode) {
        this.success = success;
        this.errorCode = errorCode;
    }

    public long getReservationID() {
        return reservationID;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation reservation = (Reservation) o;
        return reservationID == reservation.reservationID &&
                success == reservation.success &&
                errorCode == reservation.errorCode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(reservationID, success, errorCode);
    }

    @Override
    public String toString() {
        return "Reserve{" +
                "reservationID=" + reservationID +
                ", success=" + success +
                ", errorCode=" + errorCode +
                '}';
    }
}
