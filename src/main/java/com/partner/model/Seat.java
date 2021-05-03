package com.partner.model;

import java.util.Objects;

public class Seat {

    private String id;
    private long price;
    private String currency;
    private boolean reserved;

    public Seat() {
    }

    public Seat(String id, long price, String currency, boolean reserved) {
        this.id = id;
        this.price = price;
        this.currency = currency;
        this.reserved = reserved;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seat seat = (Seat) o;
        return id == seat.id &&
                price == seat.price &&
                reserved == seat.reserved &&
                Objects.equals(currency, seat.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price, currency, reserved);
    }

    @Override
    public String toString() {
        return "Seat{" +
                "id=" + id +
                ", price=" + price +
                ", currency='" + currency + '\'' +
                ", reserved=" + reserved +
                '}';
    }
}
