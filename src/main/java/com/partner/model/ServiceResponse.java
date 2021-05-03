package com.partner.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.Objects;
import java.util.Random;

/**
 * Generic class to use as a JSON response object
 * @param <T>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServiceResponse<T> implements Serializable {

    private static final long serialVersionUID = 324234234234211L;

    private T data;
    private boolean success;
    private String errorMessage;
    private Long errorCode;
    private Long reservationId;


    public ServiceResponse() {
    }

    public ServiceResponse(boolean success) {
        this.success = success;
        if (success) {
            Random random = new Random();
            this.reservationId = Math.abs(random.nextLong());
        }
    }

    public ServiceResponse(T data) {
        this.data = data;
    }

    public ServiceResponse(T data, boolean success) {
        this.data = data;
        this.success = success;
    }

    public ServiceResponse(String errorMessage, long errorCode) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    public ServiceResponse(T data, String errorMessage, long errorCode) {
        this.data = data;
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    public ServiceResponse(T data, boolean success, String errorMessage, long errorCode) {
        this.data = data;
        this.success = success;
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Long getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Long errorCode) {
        this.errorCode = errorCode;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceResponse<?> that = (ServiceResponse<?>) o;
        return success == that.success &&
                errorCode == that.errorCode &&
                Objects.equals(data, that.data) &&
                Objects.equals(errorMessage, that.errorMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, success, errorMessage, errorCode);
    }

    @Override
    public String toString() {
        return "ServiceResponse{" +
                "data=" + data +
                ", success=" + success +
                ", errorMessage='" + errorMessage + '\'' +
                ", errorCode=" + errorCode +
                '}';
    }
}
